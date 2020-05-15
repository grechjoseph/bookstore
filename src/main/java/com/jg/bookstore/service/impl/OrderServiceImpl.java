package com.jg.bookstore.service.impl;

import com.jg.bookstore.config.security.context.ContextHolder;
import com.jg.bookstore.domain.entity.Book;
import com.jg.bookstore.domain.entity.OrderEntry;
import com.jg.bookstore.domain.entity.PurchaseOrder;
import com.jg.bookstore.domain.enums.OrderStatus;
import com.jg.bookstore.domain.exception.BaseException;
import com.jg.bookstore.domain.repository.BookRepository;
import com.jg.bookstore.domain.repository.OrderRepository;
import com.jg.bookstore.service.ForexService;
import com.jg.bookstore.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

import static com.jg.bookstore.domain.enums.OrderStatus.*;
import static com.jg.bookstore.domain.exception.ErrorCode.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final ForexService forexService;
    private final OrderRepository orderRepository;
    private final BookRepository bookRepository;

    @Override
    public PurchaseOrder createOrder(final Set<OrderEntry> orderEntries) {
        log.debug("Creating Purchase Order.");
        orderEntries.forEach(orderEntry ->
                orderEntry.setBook(bookRepository.findByIdAndDeletedFalse(orderEntry.getBookId())
                        .orElseThrow(() -> new BaseException(BOOK_NOT_FOUND))));
        validateOrderEntries(orderEntries);
        final PurchaseOrder purchaseOrder = new PurchaseOrder();
        orderEntries.forEach(purchaseOrder::addOrderEntry);
        return save(purchaseOrder);
    }

    @Override
    public PurchaseOrder getOrderById(final UUID orderId) {
        log.debug("Retrieving Purchase Order by ID [{}].", orderId);
        return findById(orderId);
    }

    @Override
    public List<PurchaseOrder> getOrders() {
        log.debug("Retrieving Purchase Orders.");
        return findAll();
    }

    @Override
    public PurchaseOrder updateOrderItems(final UUID orderId, final Set<OrderEntry> orderEntries) {
        log.debug("Updating Order Entries for Purchase Order with ID [{}].", orderId);
        orderEntries.forEach(orderEntry ->
                orderEntry.setBook(bookRepository.findByIdAndDeletedFalse(orderEntry.getBookId())
                        .orElseThrow(() -> new BaseException(BOOK_NOT_FOUND))));
        validateOrderEntries(orderEntries);
        final PurchaseOrder purchaseOrder = getOrderById(orderId);

        if(!purchaseOrder.getOrderStatus().isUpdatable()) {
            throw new BaseException(ORDER_INVALID_STATUS);
        }

        log.debug("Clearing old Order Entries for Purchase Order with ID [{}].", orderId);
        for(Iterator<OrderEntry> featureIterator = purchaseOrder.getOrderEntries().iterator(); featureIterator.hasNext(); ) {
            featureIterator.next();
            featureIterator.remove();
        }
        orderRepository.flush();
        log.debug("Adding new Order Entries for Purchase Order with ID [{}].", orderId);
        orderEntries.forEach(purchaseOrder::addOrderEntry);
        purchaseOrder.setOrderStatus(CREATED);
        return save(purchaseOrder);
    }

    @Override
    public PurchaseOrder updateOrderStatus(final UUID orderId, final OrderStatus orderStatus) {
        final PurchaseOrder purchaseOrder = getOrderById(orderId);
        log.debug("Setting Purchase Order with ID [{}] from {} to {}.", orderId, purchaseOrder.getOrderStatus(), orderStatus);
        validateOrderStatusTransition(purchaseOrder, orderStatus);

        switch(orderStatus) {
            case CONFIRMED:
                return confirmOrder(purchaseOrder);
            case REFUNDED:
                return refundOrder(purchaseOrder);
            case CANCELLED:
                return cancelOrder(purchaseOrder);
            case SHIPPED:
            case PAID:
                purchaseOrder.setOrderStatus(orderStatus);
                return save(purchaseOrder);
            default:
                log.warn("Unrecognised Order Status provided.");
                return findById(purchaseOrder.getId());
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
            lockAndSubtractStockByQuantities(purchaseOrder.getOrderEntries());
        } catch (final BaseException exception) {
                /*
                    When an PurchaseOrder is CONFIRMED, but stock is insufficient:
                    1. Stock is not adjusted.
                    2. PurchaseOrder is put back in CREATED state for editing.
                 */
            if (exception.getErrorCode().equals(ORDER_NOT_ENOUGH_STOCK)) {
                log.warn("Reverting Purchase Order with ID [{}] to 'CREATED' since its requested stock is not fully available.", purchaseOrder.getId());
                purchaseOrder.setOrderStatus(CREATED);
                save(purchaseOrder);
            }

            if (exception.getErrorCode().equals(BOOK_NOT_FOUND)) {
                log.warn("Reverting Purchase Order with ID [{}] to 'CREATED' since its requested items are not fully available.", purchaseOrder.getId());
                purchaseOrder.setOrderStatus(CREATED);
                save(purchaseOrder);
            }

            throw exception;
        }

        purchaseOrder.setOrderStatus(CONFIRMED);
        return save(purchaseOrder);
    }

    private PurchaseOrder refundOrder(final PurchaseOrder purchaseOrder) {
        /*
            When a PurchaseOrder is REFUNDED, stock would have already been adjusted since REFUNDED can only be
            reached after PAID, and PAID can only be reached after CONFIRMED (which adjusts stock). Therefore:
            1. Reverse Books stock adjustments.
         */
        reverseOrderStock(purchaseOrder);
        purchaseOrder.setOrderStatus(REFUNDED);
        return save(purchaseOrder);
    }

    private PurchaseOrder cancelOrder(final PurchaseOrder purchaseOrder) {
        /*
            When a PurchaseOrder is CANCELLED after being CONFIRMED:
            1. Reverse Books stock adjustments.
            Otherwise, when CANCELLED after being CREATED, no stock needs adjusting.
         */
        if (purchaseOrder.getOrderStatus().equals(CONFIRMED)) {
            log.debug("Detected Purchase Order status transition [{} -> {}]. Reverting Book Stocks.", CONFIRMED, CANCELLED);
            reverseOrderStock(purchaseOrder);
        }
        purchaseOrder.setOrderStatus(CANCELLED);
        return save(purchaseOrder);
    }

    private void reverseOrderStock(final PurchaseOrder purchaseOrder) {
        // Negate quantities in Order Entries for adjustment.
        purchaseOrder.getOrderEntries().forEach(orderEntry -> orderEntry.setQuantity(Math.negateExact(orderEntry.getQuantity())));
        lockAndSubtractStockByQuantities(purchaseOrder.getOrderEntries());
        // Restore original quantities in Order Entries.
        purchaseOrder.getOrderEntries().forEach(orderEntry -> orderEntry.setQuantity(Math.negateExact(orderEntry.getQuantity())));
    }

    private void validateOrderEntries(final Set<OrderEntry> orderEntries) {
        if(orderEntries.isEmpty()) {
            throw new BaseException(ORDER_EMPTY);
        }

        if(orderEntries.stream().anyMatch(orderEntry -> orderEntry.getQuantity() < 1)) {
            throw new BaseException(ORDER_ENTRY_QUANTITY_ERROR);
        }
    }

    @Transactional
    private void lockAndSubtractStockByQuantities(final Set<OrderEntry> orderEntries) throws BaseException {
        log.debug("Updating Book stocks.");
        final Set<Book> booksToUpdate = new HashSet<>();

        orderEntries.forEach(orderEntry -> {
            final Book book = bookRepository.findByIdAndLock(orderEntry.getBook().getId())
                    .orElseThrow(() -> new BaseException(BOOK_NOT_FOUND));

            // If stock is being taken, check that the Book is not deleted.
            // Otherwise, if stock is being put back, deleted or not should not matter.
            if(orderEntry.getQuantity() > 0 && book.isDeleted()) {
                log.warn("Book with ID [{}] is no longer available.", orderEntry.getBook().getId());
                throw new BaseException(BOOK_NOT_FOUND);
            }

            if(book.getStock() < orderEntry.getQuantity()) {
                log.warn("Not enough stock of Book [{}], wanted [{}] - available [{}].", book.getId(), orderEntry.getQuantity(), book.getStock());
                throw new BaseException(ORDER_NOT_ENOUGH_STOCK);
            }

            book.setStock(book.getStock() - orderEntry.getQuantity());
            booksToUpdate.add(book);
        });

        log.debug("Updating Books.");
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

    private List<PurchaseOrder> findAll() {
        return orderRepository.findAll().stream().map(this::processPrices).collect(Collectors.toList());
    }

    private PurchaseOrder findById(final UUID orderId) {
        return processPrices(orderRepository.findById(orderId).orElseThrow(() -> new BaseException(ORDER_NOT_FOUND)));
    }

    private PurchaseOrder save(final PurchaseOrder purchaseOrder) {
        return processPrices(orderRepository.save(purchaseOrder));
    }

    private PurchaseOrder processPrices(final PurchaseOrder purchaseOrder) {
        purchaseOrder.setTotalPrice(BigDecimal.valueOf(purchaseOrder.getOrderEntries().stream()
                .mapToDouble(orderEntry -> orderEntry.getQuantity() * orderEntry.getBook().getPrice().doubleValue()).sum())
                .setScale(2, RoundingMode.HALF_UP));
        return processForex(purchaseOrder);
    }

    private PurchaseOrder processForex(final PurchaseOrder purchaseOrder) {
        final Currency displayCurrency = null; // TODO ContextHolder.getContext().getDisplayCurrency();

        if(Objects.nonNull(displayCurrency)){
            purchaseOrder.setConvertedPrice(forexService.convert(purchaseOrder.getTotalPrice(), displayCurrency));
            purchaseOrder.getOrderEntries().stream()
                    .filter(orderEntry -> Objects.nonNull(orderEntry.getFinalUnitPrice()))
                    .forEach(orderEntry -> orderEntry.setConvertedFinalUnitPrice(
                            forexService.convert(orderEntry.getFinalUnitPrice(), displayCurrency)));
        }

        return purchaseOrder;
    }
}
