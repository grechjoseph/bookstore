package com.jg.bookstore.api.model.orderentry;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@ApiModel(value = "Object representing an Order Entry being read.")
public class ApiOrderEntryExtended extends ApiOrderEntry {

    @ApiModelProperty(value = "Order Entry's ID.", example = "064f4cfb-5bcc-44e5-96cd-780830586eb8" )
    private final UUID id;

    @ApiModelProperty(value = "Order Entry's Final Price (established when the Order is CONFIRMED).", example = "10.95" )
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
