package com.jg.bookstore.api.dto.order;

import com.jg.bookstore.api.dto.orderentry.ApiOrderEntryExtended;
import com.jg.bookstore.domain.enums.OrderStatus;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Set;
import java.util.UUID;

@Data
@ApiModel(value = "Object representing a Purchase Order being read.")
public class ApiPurchaseOrderExtended {

    @ApiModelProperty(value = "Purchase Order's ID.")
    private final UUID id;

    @ApiModelProperty(value = "Purchase Order's status.")
    private final OrderStatus orderStatus;

    @ApiModelProperty(value = "Purchase Order's Order Entries.")
    private final Set<ApiOrderEntryExtended> orderEntries;

}
