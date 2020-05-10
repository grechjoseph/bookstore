package com.jg.bookstore.domain.entity;

import com.jg.bookstore.domain.enums.OrderStatus;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.EAGER;

@Data
@Entity
public class PurchaseOrder {

    @Id
    private UUID id = UUID.randomUUID();

    private LocalDateTime dateTime = LocalDateTime.now();

    @OneToMany(cascade = ALL, orphanRemoval = true, fetch = EAGER)
    @JoinColumn(name = "purchase_order_id", nullable = false) // nullable = false to populate FK on OrderEntry.
    private Set<OrderEntry> orderEntries = new HashSet<>();

    @Enumerated(value = EnumType.STRING)
    private OrderStatus orderStatus = OrderStatus.CREATED;

    @Transient
    private BigDecimal totalPrice;

    @Transient
    private BigDecimal convertedPrice;

    // Restrict access.
    private void setOrderEntries(final Set<OrderEntry> orderEntries) {}

    public void addOrderEntry(final OrderEntry orderEntry) {
        orderEntries.add(orderEntry);
    }

}
