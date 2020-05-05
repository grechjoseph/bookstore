package com.jg.bookstore.service.impl;

import com.jg.bookstore.domain.entity.Book;
import com.jg.bookstore.domain.entity.OrderEntry;
import com.jg.bookstore.domain.entity.PurchaseOrder;
import com.jg.bookstore.domain.enums.OrderStatus;
import com.jg.bookstore.domain.exception.BaseException;
import com.jg.bookstore.domain.repository.BookRepository;
import com.jg.bookstore.domain.repository.OrderRepository;
import com.jg.bookstore.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import static com.jg.bookstore.domain.enums.OrderStatus.*;
import static com.jg.bookstore.domain.exception.ErrorCode.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final BookRepository bookRepository;

    @Override
    public PurchaseOrder createOrder(final Set<OrderEntry> orderEntries) {
        validateOrderEntries(orderEntries);
        final PurchaseOrder purchaseOrder = new PurchaseOrder();
        purchaseOrder.setOrderEntries(orderEntries);
        return orderRepository.save(purchaseOrder);
    }

    @Override
    public PurchaseOrder getOrderById(final UUID orderId) {
        return orderRepository.findById(orderId).orElseThrow(() -> new BaseException(ORDER_NOT_FOUND));
    }

    @Override
    public List<PurchaseOrder> getOrders() {
        return orderRepository.findAll();
    }

    @Override
    public PurchaseOrder updateOrderItems(final UUID orderId, final Set<OrderEntry> orderEntries) {
        validateOrderEntries(orderEntries);
        final PurchaseOrder purchaseOrder = getOrderById(orderId);

        if(purchaseOrder.getOrderStatus().isFinal()) {
            throw new BaseException(ORDER_INVALID_STATUS);
        }

        purchaseOrder.getOrderEntries().clear();
        // This flush is required to save the deletion of the older items, since otherwise, the unique constraint will be hit.
        orderRepository.flush();
        purchaseOrder.getOrderEntries().addAll(orderEntries);
        return orderRepository.save(purchaseOrder);
    }

    @Override
    public PurchaseOrder updateOrderStatus(final UUID orderId, final OrderStatus orderStatus) {
        final PurchaseOrder purchaseOrder = getOrderById(orderId);
        validateOrderStatusTransition(purchaseOrder, orderStatus);

        switch(orderStatus) {
            case CONFIRMED:
                return confirmOrder(purchaseOrder);
            case PAID:
                purchaseOrder.setOrderStatus(PAID);
                return orderRepository.save(purchaseOrder);
            case REFUNDED:
                return refundOrder(purchaseOrder);
            case CANCELLED:
                return cancelOrder(purchaseOrder);
            default:
                return purchaseOrder;
        }
    }

    private PurchaseOrder confirmOrder(final PurchaseOrder purchaseOrder) {
        /*
            When a PurchaseOrder is CONFIRMED:
            1. Stock is committed to the PurchaseOrder, ie: Books Stock is adjusted.
            2. Price at the time is committed to the PurchaseOrder (payment is done depending on price at this stage).
         */
        purchaseOrder.getOrderEntries().forEach(orderEntry -> {
            orderEntry.setFinalUnitPrice(orderEntry.getBook().getPrice());
        });

        try {
            lockAndUpdateStock(purchaseOrder.getOrderEntries());
        } catch (final BaseException exception) {
                /*
                    When an PurchaseOrder is CONFIRMED, but stock is insufficient:
                    1. Stock is not adjusted.
                    2. PurchaseOrder is put back in CREATED state for editing.
                 */
            if(exception.getErrorCode().equals(ORDER_NOT_ENOUGH_STOCK)) {
                purchaseOrder.setOrderStatus(CREATED);
                orderRepository.save(purchaseOrder);
            }

            throw exception;
        }

        purchaseOrder.setOrderStatus(CONFIRMED);
        return orderRepository.save(purchaseOrder);
    }

    private PurchaseOrder refundOrder(final PurchaseOrder purchaseOrder) {
        /*
            When a PurchaseOrder is REFUNDED, stock would have already been adjusted since REFUNDED can only be
            reached after PAID, and PAID can only be reached after CONFIRMED (which adjusts stock). Therefore:
            1. Reverse Books stock adjustments.
         */
        reverseOrderStock(purchaseOrder);
        purchaseOrder.setOrderStatus(REFUNDED);
        return orderRepository.save(purchaseOrder);
    }

    private PurchaseOrder cancelOrder(final PurchaseOrder purchaseOrder) {
        /*
            When a PurchaseOrder is CANCELLED after being CONFIRMED:
            1. Reverse Books stock adjustments.
            Otherwise, when CANCELLED after being CREATED, no stock was ever adjusted.
         */
        if (purchaseOrder.getOrderStatus().equals(CONFIRMED)) {
            reverseOrderStock(purchaseOrder);
            purchaseOrder.setOrderStatus(CANCELLED);
            return orderRepository.save(purchaseOrder);
        }

        return purchaseOrder;
    }

    private void reverseOrderStock(final PurchaseOrder purchaseOrder) {
        // Negate quantities in Order Entries for adjustment.
        purchaseOrder.getOrderEntries().forEach(orderEntry -> orderEntry.setQuantity(Math.negateExact(orderEntry.getQuantity())));
        lockAndUpdateStock(purchaseOrder.getOrderEntries());
        // Restore original quantities in Order Entries.
        purchaseOrder.getOrderEntries().forEach(orderEntry -> orderEntry.setQuantity(Math.negateExact(orderEntry.getQuantity())));
    }

    private void validateOrderEntries(final Set<OrderEntry> orderEntries) {
        if(orderEntries.isEmpty()) {
            throw new BaseException(ORDER_EMPTY);
        }
    }

    @Transactional
    private void lockAndUpdateStock(final Set<OrderEntry> orderEntries) throws BaseException {
        final Set<Book> booksToUpdate = new HashSet<>();

        orderEntries.forEach(orderEntry -> {
            final Book book = bookRepository.findByIdAndLock(orderEntry.getBook().getId());

            if(book.getStock() < orderEntry.getQuantity()) {
                log.warn("Not enough stock of Book [{}], wanted [{}] - available [{}].", book.getId(), orderEntry.getQuantity(), book.getStock());
                throw new BaseException(ORDER_NOT_ENOUGH_STOCK);
            }

            book.setStock(book.getStock() - orderEntry.getQuantity());
            booksToUpdate.add(book);
        });

        log.info("Updating Books.");
        booksToUpdate.forEach(book -> log.debug("Book [{}] with new stock [{}].", book.getId(), book.getStock()));
        bookRepository.saveAll(booksToUpdate);
    }

    private void validateOrderStatusTransition(final PurchaseOrder purchaseOrder, final OrderStatus newOrderStatus) {
        if(purchaseOrder.getOrderStatus().getNextSteps() == null) {
            throw new BaseException(ORDER_IS_FINALISED);
        }

        if(!purchaseOrder.getOrderStatus().getNextSteps().contains(newOrderStatus)) {
            throw new BaseException(ORDER_INVALID_STATUS);
        }
    }
}
