package com.jg.bookstore.api.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.UUID;

@Data
@ApiModel(value = "Book object.")
public class ApiBook {

    @ApiModelProperty(value = "Book's ID.", example = "064f4cfb-5bcc-44e5-96cd-780830586eb8" )
    private UUID id;

    @NotEmpty(message = "Book's Name has to be between 1 and 100 characters.")
    @Size(min = 1, max = 100, message = "Book's Name has to be between 1 and 100 characters.")
    @ApiModelProperty(value = "Book's name.", example = "Dante's Inferno")
    private String name;

    @Positive(message = "Book's Stock has to be positive.")
    @ApiModelProperty(value = "Book's available stock.", example = "100" )
    private Integer stock;

    @Positive(message = "Book's Price has to be positive.")
    @ApiModelProperty(value = "Book's base currency price.", example = "10.95" )
    private BigDecimal price;

    @Positive(message = "Book's Converted Price has to be positive.")
    @ApiModelProperty(value = "Book's base currency price.", example = "10.95" )
    private BigDecimal convertedPrice;

}
