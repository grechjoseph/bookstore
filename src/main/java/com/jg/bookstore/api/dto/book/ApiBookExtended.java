package com.jg.bookstore.api.dto.book;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@ApiModel(value = "Object representing a Book being read.")
public class ApiBookExtended extends ApiBook {

    @ApiModelProperty(value = "Book's ID.", example = "064f4cfb-5bcc-44e5-96cd-780830586eb8" )
    private final UUID id;

    public ApiBookExtended(final UUID id,
                           final String name,
                           final Integer stock,
                           final BigDecimal price) {
        super(name, stock, price);
        this.id = id;
    }
}
