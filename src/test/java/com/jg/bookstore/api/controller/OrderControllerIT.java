package com.jg.bookstore.api.controller;

import com.jg.bookstore.BaseTestContext;
import com.jg.bookstore.api.model.order.ApiPurchaseOrderExtended;
import com.jg.bookstore.api.model.orderentry.ApiOrderEntry;
import com.jg.bookstore.domain.repository.AuthorRepository;
import com.jg.bookstore.domain.repository.BookRepository;
import com.jg.bookstore.domain.repository.OrderRepository;
import com.jg.bookstore.mapper.ModelMapper;
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

    @Autowired
    private ModelMapper mapper;

    @BeforeEach
    public void before() {
        authorRepository.save(AUTHOR);
        bookRepository.save(BOOK);
    }

    @Test
    public void createOrder_shouldCreateAndReturnOrder() {
        final ApiOrderEntry[] requestBody = {mapper.map(ORDER_ENTRY, ApiOrderEntry.class)};
        final ApiPurchaseOrderExtended result = doRequest(POST, "/orders", requestBody, ApiPurchaseOrderExtended.class);
        final ApiPurchaseOrderExtended expected = mapper.map(ORDER, ApiPurchaseOrderExtended.class);
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
        final ApiPurchaseOrderExtended result = doRequest(GET, "/orders/" + ORDER_ID, null, ApiPurchaseOrderExtended.class);
        assertThat(result).isEqualTo(mapper.map(ORDER, ApiPurchaseOrderExtended.class));
    }

    @Test
    public void getOrders_shouldReturnOrders() {
        orderRepository.save(ORDER);
        final ApiPurchaseOrderExtended[] expected = new ApiPurchaseOrderExtended[] { mapper.map(ORDER, ApiPurchaseOrderExtended.class) };
        final ApiPurchaseOrderExtended[] result = doRequest(GET, "/orders", null, ApiPurchaseOrderExtended[].class);
        assertThat(result).isEqualTo(expected);
    }

    @Test
    public void updateOrderItems_shouldUpdateAndReturnOrder() {
        orderRepository.save(ORDER);
        ORDER_ENTRY.setQuantity(10);
        final ApiOrderEntry[] requestBody = {mapper.map(ORDER_ENTRY, ApiOrderEntry.class)};
        final ApiPurchaseOrderExtended result = doRequest(PUT, "/orders/" + ORDER_ID, requestBody, ApiPurchaseOrderExtended.class);
        assertThat(result).isEqualTo(mapper.map(ORDER, ApiPurchaseOrderExtended.class));
    }

    @Test
    public void updateOrderStatus_shouldUpdateStatusAndReturnOrder() {
        orderRepository.save(ORDER);
        final ApiPurchaseOrderExtended result = doRequest(PUT, "/orders/" + ORDER_ID + "/status", CONFIRMED, ApiPurchaseOrderExtended.class);
        ORDER.setOrderStatus(CONFIRMED);
        assertThat(result).isEqualTo(mapper.map(ORDER, ApiPurchaseOrderExtended.class));
    }

}
