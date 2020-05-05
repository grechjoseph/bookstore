package com.jg.bookstore.api.controller;

import com.jg.bookstore.api.dto.order.ApiPurchaseOrderExtended;
import com.jg.bookstore.api.dto.orderentry.ApiOrderEntry;
import com.jg.bookstore.domain.entity.OrderEntry;
import com.jg.bookstore.domain.enums.OrderStatus;
import com.jg.bookstore.mapper.ModelMapper;
import com.jg.bookstore.service.BookService;
import com.jg.bookstore.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {

    private final BookService bookService;
    private final OrderService orderService;
    private final ModelMapper modelMapper;

    @PostMapping
    public ApiPurchaseOrderExtended createOrder(@RequestBody final List<ApiOrderEntry> orderEntries) {
        return modelMapper.map(orderService.createOrder(mapApiOrderEntriesToOrderEntries(orderEntries)), ApiPurchaseOrderExtended.class);
    }

    @GetMapping("/{orderId}")
    public ApiPurchaseOrderExtended getOrderById(@PathVariable final UUID orderId) {
        return modelMapper.map(orderService.getOrderById(orderId), ApiPurchaseOrderExtended.class);
    }

    @GetMapping
    public List<ApiPurchaseOrderExtended> getOrders() {
        return modelMapper.mapAsList(orderService.getOrders(), ApiPurchaseOrderExtended.class);
    }

    @PutMapping("/{orderId}")
    public ApiPurchaseOrderExtended updatedOrderItems(@PathVariable final UUID orderId, @RequestBody final List<ApiOrderEntry> orderEntries) {
        return modelMapper.map(
                orderService.updateOrderItems(orderId, mapApiOrderEntriesToOrderEntries(orderEntries)),
                ApiPurchaseOrderExtended.class);
    }

    @PutMapping("/{orderId}/status")
    public ApiPurchaseOrderExtended updateOrderStatus(@PathVariable final UUID orderId, @RequestBody final OrderStatus orderStatus) {
        return modelMapper.map(orderService.updateOrderStatus(orderId, orderStatus), ApiPurchaseOrderExtended.class);
    }

    private Set<OrderEntry> mapApiOrderEntriesToOrderEntries(final List<ApiOrderEntry> orderEntries) {
        return orderEntries.stream().map(orderEntry -> {
            final OrderEntry entry = new OrderEntry();
            entry.setBook(bookService.getBookById(orderEntry.getBookId()));
            entry.setQuantity(orderEntry.getQuantity());
            return entry;
        }).collect(Collectors.toSet());
    }

}
