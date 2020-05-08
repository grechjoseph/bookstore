package com.jg.bookstore.api.model.order;

import com.jg.bookstore.api.model.orderentry.ApiOrderEntry;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

@Data
@ApiModel(value = "Object representing a Purchase Order being created or updated.")
public class ApiPurchaseOrder {

    @NotEmpty
    @ApiModelProperty(value = "Purchase Order's Order Entries.")
    private final List<ApiOrderEntry> orderEntries = new ArrayList<>();

}
