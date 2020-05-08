package com.jg.bookstore.api.model.book;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Data
@ApiModel(value = "Object representing a Book being created or updated.")
public class ApiBook {

    @NotEmpty(message = "Book's Name has to be between 1 and 100 characters.")
    @Size(min = 1, max = 100, message = "Book's Name has to be between 1 and 100 characters.")
    @ApiModelProperty(value = "Book's name.", example = "Dante's Inferno")
    private final String name;

    @Positive(message = "Book's Stock has to be positive.")
    @ApiModelProperty(value = "Book's available stock.", example = "100" )
    private final Integer stock;

    @Positive(message = "Book's Price has to be positive.")
    @ApiModelProperty(value = "Book's base currency price.", example = "10.95" )
    private final BigDecimal price;

}
