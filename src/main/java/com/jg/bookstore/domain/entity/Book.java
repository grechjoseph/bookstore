package com.jg.bookstore.domain.entity;

import lombok.Data;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;
import java.util.UUID;

@Data
@Entity
@ToString(exclude = "author")
public class Book {

    @Id
    private UUID id = UUID.randomUUID();

    private boolean deleted;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private Author author;

    @NotEmpty
    private String name;

    private Integer stock;
    private BigDecimal price;

}
