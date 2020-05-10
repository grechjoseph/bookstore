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
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
@ApiOperation(value = "Manage Orders.")
public class OrderController {

    private final BookService bookService;
    private final OrderService orderService;
    private final ModelMapper mapper;

    @PostMapping
    @ApiOperation(value = "Create an Order.")
    public ApiPurchaseOrder createOrder(@RequestBody final List<ApiOrderEntry> orderEntries) {
        return mapper.map(orderService.createOrder(mapper.mapAsSet(orderEntries, OrderEntry.class)), ApiPurchaseOrder.class);
    }

    @GetMapping("/{orderId}")
    @ApiOperation(value = "Get an Order by its ID.")
    public ApiPurchaseOrder getOrderById(@PathVariable final UUID orderId) {
        return mapper.map(orderService.getOrderById(orderId), ApiPurchaseOrder.class);
    }

    @GetMapping
    @ApiOperation(value = "Get Orders.")
    public List<ApiPurchaseOrder> getOrders() {
        return mapper.mapAsList(orderService.getOrders(), ApiPurchaseOrder.class);
    }

    @PutMapping("/{orderId}")
    @ApiOperation(value = "Update an Order's items.")
    public ApiPurchaseOrder updatedOrderItems(@PathVariable final UUID orderId, @RequestBody final List<ApiOrderEntry> orderEntries) {
        return mapper.map(
                orderService.updateOrderItems(orderId, mapper.mapAsSet(orderEntries, OrderEntry.class)),
                ApiPurchaseOrder.class);
    }

    @PutMapping("/{orderId}/status")
    @ApiOperation(value = "Update an Order's status. The status can go from CREATED to CONFIRMED or CANCELLED, from CONFIRMED to PAID or CANCELLED, from PAID to REFUNDED or SHIPPED. " +
            "Confirming an Order commits the stock to that Order, while Cancelling after Confirming, or Refunding an order, un-commits the stock.")
    public ApiPurchaseOrder updateOrderStatus(@PathVariable final UUID orderId, @RequestBody final OrderStatus orderStatus) {
        return mapper.map(orderService.updateOrderStatus(orderId, orderStatus), ApiPurchaseOrder.class);
    }

}
