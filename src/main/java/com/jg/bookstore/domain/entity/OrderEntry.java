package com.jg.bookstore.domain.entity;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.UUID;

import static javax.persistence.FetchType.LAZY;

@Data
@Entity
@Table(name = "order_entry")
public class OrderEntry {

    @Id
    private UUID id = UUID.randomUUID();

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "book_id")
    private Book book;

    private Integer quantity;
    private BigDecimal finalUnitPrice;

    @Transient
    private BigDecimal convertedFinalUnitPrice;

    @Transient
    private UUID bookId;

}
