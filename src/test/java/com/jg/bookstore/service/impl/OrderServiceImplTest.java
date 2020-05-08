package com.jg.bookstore.service.impl;

import com.jg.bookstore.domain.entity.OrderEntry;
import com.jg.bookstore.domain.entity.PurchaseOrder;
import com.jg.bookstore.domain.enums.OrderStatus;
import com.jg.bookstore.domain.exception.BaseException;
import com.jg.bookstore.domain.repository.BookRepository;
import com.jg.bookstore.domain.repository.OrderRepository;
import com.jg.bookstore.utils.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

import static com.jg.bookstore.domain.enums.OrderStatus.*;
import static com.jg.bookstore.domain.exception.ErrorCode.*;
import static com.jg.bookstore.utils.TestUtils.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class OrderServiceImplTest {

    @Mock
    private OrderRepository mockOrderRepository;

    @Mock
    private BookRepository mockBookRepository;

    @InjectMocks
    private OrderServiceImpl orderService;

    @BeforeEach
    public void beforeEach() {
        TestUtils.reset();
        when(mockOrderRepository.findById(ORDER_ID)).thenReturn(Optional.of(ORDER));
        when(mockOrderRepository.findAll()).thenReturn(List.of(ORDER));
        when(mockOrderRepository.save(any(PurchaseOrder.class))).thenAnswer(invocation -> invocation.getArguments()[0]);
        when(mockBookRepository.findByIdAndLock(BOOK_ID)).thenReturn(Optional.of(BOOK));
    }

    @Test
    public void createOrder_validOrderEntries_shouldCreateOrder() {
        final PurchaseOrder result = orderService.createOrder(Set.of(ORDER_ENTRY));
        verify(mockOrderRepository).save(any(PurchaseOrder.class));
        assertThat(result.getOrderEntries()).isEqualTo(Set.of(ORDER_ENTRY));
        assertThat(result.getDateTime()).isNotNull();
        assertThat(result.getOrderStatus()).isEqualTo(CREATED);
    }

    @Test
    public void createOrder_noOrderEntries_shouldThrowOrderEmptyException() {
        assertThatThrownBy(() -> orderService.createOrder(Set.of()))
                .isEqualTo(new BaseException(ORDER_EMPTY));
        verify(mockOrderRepository, never()).save(any(PurchaseOrder.class));
    }

    @Test
    public void createOrder_zeroQuantity_shouldThrowOrderEntryQuantityException() {
        ORDER_ENTRY.setQuantity(0);
        assertThatThrownBy(() -> orderService.createOrder(Set.of(ORDER_ENTRY)))
                .isEqualTo(new BaseException(ORDER_ENTRY_QUANTITY_ERROR));
        verify(mockOrderRepository, never()).save(any(PurchaseOrder.class));
    }

    @Test
    public void getOrderById_orderExists_shouldReturnOrder() {
        final PurchaseOrder result = orderService.getOrderById(ORDER_ID);
        verify(mockOrderRepository).findById(ORDER_ID);
        assertThat(result).isEqualTo(ORDER);
    }

    @Test
    public void getOrderById_orderDoesNotExist_shouldThrowOrderNotFoundException() {
        when(mockOrderRepository.findById(ORDER_ID)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> orderService.getOrderById(ORDER_ID))
                .isEqualTo(new BaseException(ORDER_NOT_FOUND));
        verify(mockOrderRepository).findById(ORDER_ID);
    }

    @Test
    public void getOrders_ordersExist_shouldReturnListOfOrders() {
        final List<PurchaseOrder> result = orderService.getOrders();
        verify(mockOrderRepository).findAll();
        assertThat(result).isEqualTo(List.of(ORDER));
    }

    @Test
    public void getOrders_ordersDoNotExist_shouldReturnEmptyList() {
        when(mockOrderRepository.findAll()).thenReturn(List.of());
        final List<PurchaseOrder> result = orderService.getOrders();
        verify(mockOrderRepository).findAll();
        assertThat(result).isEqualTo(List.of());
    }

    @Test
    public void updateOrderItems_validOrderEntries_shouldUpdateOrder() {
        final OrderEntry newOrderEntry = new OrderEntry();
        newOrderEntry.setBook(BOOK);
        newOrderEntry.setQuantity(5);
        final PurchaseOrder result = orderService.updateOrderItems(ORDER_ID, Set.of(newOrderEntry));
        verify(mockOrderRepository).flush();
        verify(mockOrderRepository).save(ORDER);
        assertThat(result.getOrderEntries()).isNotEqualTo(Set.of(ORDER_ENTRY));
        assertThat(result.getOrderEntries()).isEqualTo(Set.of(newOrderEntry));
    }

    @Test
    public void updateOrderItems_emptyOrderEntries_shouldThrowOrderEmptyException() {
        assertThatThrownBy(() -> orderService.updateOrderItems(ORDER_ID, Set.of()))
                .isEqualTo(new BaseException(ORDER_EMPTY));
        verify(mockOrderRepository, never()).flush();
        verify(mockOrderRepository, never()).save(any(PurchaseOrder.class));
    }

    @Test
    public void updateOrderItems_zeroQuantity_shouldThrowOrderEntryQuantityException() {
        ORDER_ENTRY.setQuantity(0);
        assertThatThrownBy(() -> orderService.updateOrderItems(ORDER_ID, Set.of(ORDER_ENTRY)))
                .isEqualTo(new BaseException(ORDER_ENTRY_QUANTITY_ERROR));
        verify(mockOrderRepository, never()).flush();
        verify(mockOrderRepository, never()).save(any(PurchaseOrder.class));
    }

    @Test
    public void updateOrderItems_orderIsFinalised_shouldThrowInvalidStatusException() {
        List.of(PAID, CANCELLED, REFUNDED, SHIPPED).forEach(finalStatus -> {
            ORDER.setOrderStatus(finalStatus);
            assertThatThrownBy(() -> orderService.updateOrderItems(ORDER_ID, Set.of(ORDER_ENTRY)))
                    .isEqualTo(new BaseException(ORDER_INVALID_STATUS));
        });
        verify(mockOrderRepository, never()).flush();
        verify(mockOrderRepository, never()).save(any(PurchaseOrder.class));
    }

    @Test
    public void updateOrderItems_orderIsNotFinalised_shouldRevertToCreated() {
        final List<OrderStatus> notFinalStatuses = List.of(CREATED, CONFIRMED);
        notFinalStatuses.forEach(finalStatus -> {
            ORDER.setOrderStatus(finalStatus);
            final PurchaseOrder result = orderService.updateOrderItems(ORDER_ID, Set.of(ORDER_ENTRY));
            assertThat(result.getOrderStatus()).isEqualTo(CREATED);
        });

        verify(mockOrderRepository, times(notFinalStatuses.size())).flush();
        verify(mockOrderRepository, times(notFinalStatuses.size())).save(any(PurchaseOrder.class));
    }

    @Test
    public void updateOrderItems_orderDoesNotExist_shouldThrowOrderNotFoundException() {
        when(mockOrderRepository.findById(ORDER_ID)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> orderService.updateOrderItems(ORDER_ID, Set.of(ORDER_ENTRY)))
                .isEqualTo(new BaseException(ORDER_NOT_FOUND));
        verify(mockOrderRepository, never()).flush();
        verify(mockOrderRepository, never()).save(any(PurchaseOrder.class));
    }

    @Test
    public void updateOrderStatus_updateToAValidStatus_shouldUpdateStatus() {
        Arrays.stream(OrderStatus.values())
                .filter(orderStatus -> Objects.nonNull(orderStatus.getNextSteps()))
                .forEach(
                    statusWithNextSteps -> {
                        statusWithNextSteps.getNextSteps().forEach(nextStatus -> {
                            ORDER.setOrderStatus(statusWithNextSteps);
                            BOOK.setStock(BOOK_STOCK);
                            final PurchaseOrder result = orderService.updateOrderStatus(ORDER_ID, nextStatus);
                            assertThat(result.getOrderStatus()).isEqualTo(nextStatus);
                        });
                    }
        );

        verify(mockOrderRepository, atLeast(1)).save(ORDER);
        verify(mockBookRepository, atLeast(1)).saveAll(Set.of(BOOK));
    }

    @Test
    public void updateOrderStatus_updateAnInvalidStatus_shouldThrowInvalidStatusException() {
        Arrays.stream(OrderStatus.values())
                .filter(orderStatus -> Objects.nonNull(orderStatus.getNextSteps()))
                .forEach(
                    statusWithNextSteps -> {
                        Arrays.stream(OrderStatus.values())
                                .filter(nextStatus -> !statusWithNextSteps.getNextSteps().contains(nextStatus))
                                .forEach(nextStatus -> {
                                    ORDER.setOrderStatus(statusWithNextSteps);
                                    assertThatThrownBy(() -> orderService.updateOrderStatus(ORDER_ID, nextStatus))
                                            .isEqualTo(new BaseException(ORDER_INVALID_STATUS));
                                });
                    }
        );
        verify(mockOrderRepository, never()).save(ORDER);
        verify(mockBookRepository, never()).saveAll(any(Set.class));
    }

    @Test
    public void updateOrderStatus_updateFromAFinalStatus_shouldThrowOrderFinalisedException() {
        Arrays.stream(OrderStatus.values())
                .filter(orderStatus -> Objects.isNull(orderStatus.getNextSteps()))
                .forEach(
                        finalStatus -> {
                            Arrays.stream(OrderStatus.values())
                                    .forEach(nextStatus -> {
                                        ORDER.setOrderStatus(finalStatus);
                                        assertThatThrownBy(() -> orderService.updateOrderStatus(ORDER_ID, nextStatus))
                                                .isEqualTo(new BaseException(ORDER_IS_FINALISED));
                                    });
                        }
                );
        verify(mockOrderRepository, never()).save(ORDER);
        verify(mockBookRepository, never()).saveAll(any(Set.class));
    }

    @Test
    public void confirmOrder_stockNotAvailable_shouldSetBackToCreated() {
        BOOK.setStock(0);
        assertThatThrownBy(() -> orderService.updateOrderStatus(ORDER_ID, CONFIRMED))
                .isEqualTo(new BaseException(ORDER_NOT_ENOUGH_STOCK));
        assertThat(ORDER.getOrderStatus()).isEqualTo(CREATED);
    }

    @Test
    public void confirmOrder_bookDeleted_shouldSetBackToCreated() {
        BOOK.setDeleted(true);
        assertThatThrownBy(() -> orderService.updateOrderStatus(ORDER_ID, CONFIRMED))
                .isEqualTo(new BaseException(BOOK_NOT_FOUND));
        assertThat(ORDER.getOrderStatus()).isEqualTo(CREATED);
    }

    @Test
    public void refundOrder_shouldAdjustBookStocks() {
        ORDER.setOrderStatus(PAID);
        final PurchaseOrder result = orderService.updateOrderStatus(ORDER_ID, REFUNDED);
        verify(mockBookRepository, times(ORDER.getOrderEntries().size())).findByIdAndLock(BOOK_ID);
        verify(mockBookRepository).saveAll(any(Set.class));
        assertThat(BOOK.getStock()).isEqualTo(BOOK_STOCK + ORDER_ENTRY.getQuantity());
        assertThat(ORDER.getOrderStatus()).isEqualTo(REFUNDED);
        assertThat(ORDER_ENTRY.getQuantity()).isPositive();
    }

    @Test
    public void cancelOrder_orderIsCreated_shouldNotReverseBookStocks() {
        final PurchaseOrder result = orderService.updateOrderStatus(ORDER_ID, CANCELLED);
        verify(mockBookRepository, never()).findByIdAndLock(BOOK_ID);
        verify(mockBookRepository, never()).saveAll(any(Set.class));
        assertThat(BOOK.getStock()).isEqualTo(BOOK_STOCK);
        assertThat(ORDER.getOrderStatus()).isEqualTo(CANCELLED);
        assertThat(ORDER_ENTRY.getQuantity()).isPositive();
    }

    @Test
    public void cancelOrder_orderIsConfirmed_shouldReverseBookStocks() {
        ORDER.setOrderStatus(CONFIRMED);
        final PurchaseOrder result = orderService.updateOrderStatus(ORDER_ID, CANCELLED);
        verify(mockBookRepository, times(ORDER.getOrderEntries().size())).findByIdAndLock(BOOK_ID);
        verify(mockBookRepository).saveAll(any(Set.class));
        assertThat(BOOK.getStock()).isEqualTo(BOOK_STOCK + ORDER_ENTRY.getQuantity());
        assertThat(ORDER.getOrderStatus()).isEqualTo(CANCELLED);
        assertThat(ORDER_ENTRY.getQuantity()).isPositive();
    }
}
