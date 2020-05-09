package com.jg.bookstore.api.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.util.UUID;

@Data
@ApiModel(value = "Order Entry object.")
public class ApiOrderEntry {

    @ApiModelProperty(value = "Order Entry's ID.", example = "064f4cfb-5bcc-44e5-96cd-780830586eb8" )
    private UUID id;

    @NotNull
    @ApiModelProperty(value = "Order Entry's Book ID.", example = "064f4cfb-5bcc-44e5-96cd-780830586eb8" )
    private UUID bookId;

    @Positive
    @ApiModelProperty(value = "Order Entry's quantity.", example = "1" )
    private Integer quantity;

    @ApiModelProperty(value = "Order Entry's Final Price (established when the Order is CONFIRMED).", example = "10.95" )
    private BigDecimal finalUnitPrice;

    @ApiModelProperty(value = "Order Entry's Converted Final Price (established when the Order is CONFIRMED).", example = "10.95" )
    private BigDecimal convertedFinalUnitPrice;

}
