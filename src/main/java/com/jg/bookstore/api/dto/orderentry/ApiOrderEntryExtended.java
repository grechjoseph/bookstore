package com.jg.bookstore.api.dto.orderentry;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@ApiModel(value = "Object representing an Order Entry being read.")
public class ApiOrderEntryExtended extends ApiOrderEntry {

    @ApiModelProperty(value = "Order Entry's ID.")
    private final UUID id;

    @ApiModelProperty(value = "Order Entry's Final Price (established when the Order is CONFIRMED).")
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