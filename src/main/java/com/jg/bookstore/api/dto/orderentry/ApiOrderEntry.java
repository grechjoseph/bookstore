package com.jg.bookstore.api.dto.orderentry;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
@ApiModel(value = "Object representing an Order Entry being created or updated.")
public class ApiOrderEntry {

    @NotNull
    @ApiModelProperty(value = "Order Entry's Book ID.")
    private final UUID bookId;

    @ApiModelProperty(value = "Order Entry's quantity.")
    private final Integer quantity;

}
