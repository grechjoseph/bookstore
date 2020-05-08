package com.jg.bookstore.api.model.orderentry;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.UUID;

@Data
@ApiModel(value = "Object representing an Order Entry being created or updated.")
public class ApiOrderEntry {

    @NotNull
    @ApiModelProperty(value = "Order Entry's Book ID.", example = "064f4cfb-5bcc-44e5-96cd-780830586eb8" )
    private final UUID bookId;

    @Positive
    @ApiModelProperty(value = "Order Entry's quantity.", example = "1" )
    private final Integer quantity;

}
