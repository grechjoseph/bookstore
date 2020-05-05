package com.jg.bookstore.service;

import com.jg.bookstore.domain.entity.PurchaseOrder;
import com.jg.bookstore.domain.entity.OrderEntry;
import com.jg.bookstore.domain.enums.OrderStatus;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface OrderService {

    PurchaseOrder createOrder(final Set<OrderEntry> orderEntries);

    PurchaseOrder getOrderById(final UUID orderId);

    List<PurchaseOrder> getOrders();

    PurchaseOrder updateOrderItems(final UUID orderId, Set<OrderEntry> orderEntries);

    PurchaseOrder updateOrderStatus(final UUID orderId, final OrderStatus orderStatus);

}
