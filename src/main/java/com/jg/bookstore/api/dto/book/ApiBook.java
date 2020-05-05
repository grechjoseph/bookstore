package com.jg.bookstore.api.dto.book;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;

@Data
public class ApiBook {

    @NotEmpty
    private final String name;

    private final Integer stock;
    private final BigDecimal price;

}
