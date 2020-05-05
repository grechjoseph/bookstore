package com.jg.bookstore.api.dto.orderentry;

import lombok.Getter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
public class ApiOrderEntryExtended extends ApiOrderEntry {

    private final UUID id;
    private final BigDecimal finalUnitPrice;

    public ApiOrderEntryExtended(final UUID id,
            final BigDecimal finalUnitPrice,
            final UUID bookId,
            final Integer quantity) {
        super(bookId, quantity);
        this.id = id;
        this.finalUnitPrice = finalUnitPrice;
    }
}
