package com.jg.bookstore.api.controller;

import com.jg.bookstore.api.model.ApiOrderEntry;
import com.jg.bookstore.api.model.ApiPurchaseOrder;
import com.jg.bookstore.domain.entity.OrderEntry;
import com.jg.bookstore.domain.enums.OrderStatus;
import com.jg.bookstore.mapper.ModelMapper;
import com.jg.bookstore.service.BookService;
import com.jg.bookstore.service.OrderService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
@ApiOperation(value = "Manage Orders.")
public class OrderController {

    private final BookService bookService;
    private final OrderService orderService;
    private final ModelMapper modelMapper;

    @PostMapping
    @ApiOperation(value = "Create an Order.")
    public ApiPurchaseOrder createOrder(@RequestBody final List<ApiOrderEntry> orderEntries) {
        return modelMapper.map(orderService.createOrder(mapApiOrderEntriesToOrderEntries(orderEntries)), ApiPurchaseOrder.class);
    }

    @GetMapping("/{orderId}")
    @ApiOperation(value = "Get an Order by its ID.")
    public ApiPurchaseOrder getOrderById(@PathVariable final UUID orderId) {
        return modelMapper.map(orderService.getOrderById(orderId), ApiPurchaseOrder.class);
    }

    @GetMapping
    @ApiOperation(value = "Get Orders.")
    public List<ApiPurchaseOrder> getOrders() {
        return modelMapper.mapAsList(orderService.getOrders(), ApiPurchaseOrder.class);
    }

    @PutMapping("/{orderId}")
    @ApiOperation(value = "Update an Order's items.")
    public ApiPurchaseOrder updatedOrderItems(@PathVariable final UUID orderId, @RequestBody final List<ApiOrderEntry> orderEntries) {
        return modelMapper.map(
                orderService.updateOrderItems(orderId, mapApiOrderEntriesToOrderEntries(orderEntries)),
                ApiPurchaseOrder.class);
    }

    @PutMapping("/{orderId}/status")
    @ApiOperation(value = "Update an Order's status. The status can go from CREATED to CONFIRMED or CANCELLED, from CONFIRMED to PAID or CANCELLED, from PAID to REFUNDED or SHIPPED. " +
            "Confirming an Order commits the stock to that Order, while Cancelling after Confirming, or Refunding an order, un-commits the stock.")
    public ApiPurchaseOrder updateOrderStatus(@PathVariable final UUID orderId, @RequestBody final OrderStatus orderStatus) {
        return modelMapper.map(orderService.updateOrderStatus(orderId, orderStatus), ApiPurchaseOrder.class);
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
