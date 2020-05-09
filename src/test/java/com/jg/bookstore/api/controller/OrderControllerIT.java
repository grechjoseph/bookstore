package com.jg.bookstore.api.controller;

import com.jg.bookstore.BaseTestContext;
import com.jg.bookstore.api.model.ApiOrderEntry;
import com.jg.bookstore.api.model.ApiPurchaseOrder;
import com.jg.bookstore.domain.repository.AuthorRepository;
import com.jg.bookstore.domain.repository.BookRepository;
import com.jg.bookstore.domain.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static com.jg.bookstore.domain.enums.OrderStatus.CONFIRMED;
import static com.jg.bookstore.utils.TestUtils.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpMethod.*;

public class OrderControllerIT extends BaseTestContext {

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private OrderRepository orderRepository;

    @BeforeEach
    public void before() {
        authorRepository.save(AUTHOR);
        bookRepository.save(BOOK);
    }

    @Test
    public void createOrder_shouldCreateAndReturnOrder() {
        final ApiOrderEntry[] requestBody = { API_ORDER_ENTRY };
        final ApiPurchaseOrder result = doRequest(POST, "/orders", requestBody, ApiPurchaseOrder.class);
        final ApiPurchaseOrder expected = API_PURCHASE_ORDER;
        assertThat(result.getOrderEntries().size()).isEqualTo(expected.getOrderEntries().size());
        result.getOrderEntries().forEach(orderEntry -> {
            assertThat(orderEntry.getBookId()).isEqualTo(BOOK_ID);
            assertThat(orderEntry.getQuantity()).isEqualTo(ORDER_ENTRY.getQuantity());
        });
        assertThat(result.getOrderStatus()).isEqualTo(expected.getOrderStatus());
    }

    @Test
    public void getOrderById_shouldReturnOrder() {
        orderRepository.save(ORDER);
        final ApiPurchaseOrder result = doRequest(GET, "/orders/" + ORDER_ID, null, ApiPurchaseOrder.class);
        assertThat(result).isEqualTo(API_PURCHASE_ORDER);
    }

    @Test
    public void getOrders_shouldReturnOrders() {
        orderRepository.save(ORDER);
        final ApiPurchaseOrder[] expected = new ApiPurchaseOrder[] { API_PURCHASE_ORDER };
        final ApiPurchaseOrder[] result = doRequest(GET, "/orders", null, ApiPurchaseOrder[].class);
        assertThat(result).isEqualTo(expected);
    }

    @Test
    public void updateOrderItems_shouldUpdateAndReturnOrder() {
        orderRepository.save(ORDER);
        API_ORDER_ENTRY.setQuantity(10);
        final ApiOrderEntry[] requestBody = { API_ORDER_ENTRY };
        final ApiPurchaseOrder result = doRequest(PUT, "/orders/" + ORDER_ID, requestBody, ApiPurchaseOrder.class);
        assertThat(result.getOrderEntries().size()).isEqualTo(ORDER.getOrderEntries().size());
        result.getOrderEntries().forEach(orderEntry -> {
            assertThat(orderEntry.getBookId()).isEqualTo(BOOK_ID);
            assertThat(orderEntry.getQuantity()).isEqualTo(API_ORDER_ENTRY.getQuantity());
        });
        assertThat(result.getOrderStatus()).isEqualTo(ORDER.getOrderStatus());
    }

    @Test
    public void updateOrderStatus_shouldUpdateStatusAndReturnOrder() {
        orderRepository.save(ORDER);
        final ApiPurchaseOrder result = doRequest(PUT, "/orders/" + ORDER_ID + "/status", CONFIRMED, ApiPurchaseOrder.class);
        API_PURCHASE_ORDER.setOrderStatus(CONFIRMED);
        API_ORDER_ENTRY.setFinalUnitPrice(BOOK_PRICE);
        assertThat(result).isEqualTo(API_PURCHASE_ORDER);
    }

}
