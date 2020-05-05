package com.jg.bookstore.domain.entity;

import com.jg.bookstore.domain.enums.OrderStatus;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static javax.persistence.CascadeType.*;

@Data
@Entity
public class PurchaseOrder {

    @Id
    private UUID id = UUID.randomUUID();

    private LocalDateTime dateTime = LocalDateTime.now();

    @OneToMany(cascade = {PERSIST, MERGE}, orphanRemoval = true)
    @JoinColumn(name = "purchase_order_id", nullable = false) // nullable = false to populate FK on OrderEntry.
    private Set<OrderEntry> orderEntries = new HashSet<>();

    @Enumerated(value = EnumType.STRING)
    private OrderStatus orderStatus = OrderStatus.CREATED;

}
