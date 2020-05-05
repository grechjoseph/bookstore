package com.jg.bookstore.api.dto.author;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class ApiAuthor {

    @NotEmpty
    private final String firstName;

    @NotEmpty
    private final String lastName;

}
