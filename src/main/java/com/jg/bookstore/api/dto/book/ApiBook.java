package com.jg.bookstore.api.dto.book;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Data
@ApiModel(value = "Object representing a Book being created or updated.")
public class ApiBook {

    @Size(min = 1, max = 100)
    @ApiModelProperty(value = "Book's name.", example = "Dante's Inferno" )
    private final String name;

    @Positive
    @ApiModelProperty(value = "Book's available stock.", example = "100" )
    private final Integer stock;

    @Positive
    @ApiModelProperty(value = "Book's base currency price.", example = "10.95" )
    private final BigDecimal price;

}
