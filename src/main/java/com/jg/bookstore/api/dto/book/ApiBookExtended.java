package com.jg.bookstore.api.dto.book;

import lombok.Getter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
public class ApiBookExtended extends ApiBook {

    private final UUID id;

    public ApiBookExtended(final UUID id,
                           final String name,
                           final Integer stock,
                           final BigDecimal price) {
        super(name, stock, price);
        this.id = id;
    }
}
