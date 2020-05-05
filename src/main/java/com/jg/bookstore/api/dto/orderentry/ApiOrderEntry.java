package com.jg.bookstore.api.dto.orderentry;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
public class ApiOrderEntry {

    @NotNull
    private final UUID bookId;

    private final Integer quantity;

}
