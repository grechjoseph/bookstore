package com.jg.bookstore.api.dto.book;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;

@Data
@ApiModel(value = "Object representing a Book being created or updated.")
public class ApiBook {

    @ApiModelProperty(value = "Book's name.")
    @NotEmpty
    private final String name;

    @ApiModelProperty(value = "Book's available stock.")
    private final Integer stock;

    @ApiModelProperty(value = "Book's base currency price.")
    private final BigDecimal price;

}
