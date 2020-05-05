package com.jg.bookstore.api.dto.author;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
@ApiModel(value = "Object representing an Author being created or updated.")
public class ApiAuthor {

    @NotEmpty
    private final String firstName;

    @NotEmpty
    private final String lastName;

}
