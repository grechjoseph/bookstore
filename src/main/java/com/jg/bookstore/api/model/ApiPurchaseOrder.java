package com.jg.bookstore.api.model;

import com.jg.bookstore.domain.enums.OrderStatus;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@ApiModel(value = "Purchase Order object.")
public class ApiPurchaseOrder {

    @ApiModelProperty(value = "Purchase Order's ID.", example = "064f4cfb-5bcc-44e5-96cd-780830586eb8" )
    private UUID id;

    @NotEmpty
    @ApiModelProperty(value = "Purchase Order's Order Entries.")
    private List<ApiOrderEntry> orderEntries = new ArrayList<>();

    @ApiModelProperty(value = "Purchase Order's status.", example = "CREATED" )
    private OrderStatus orderStatus;

    @ApiModelProperty(value = "Purchase Order's Total Price in Base Currency.", example = "10.00" )
    private BigDecimal totalPrice;

    @ApiModelProperty(value = "Purchase Order's Total Price in Display Currency.", example = "8.50" )
    private BigDecimal convertedPrice;

}
