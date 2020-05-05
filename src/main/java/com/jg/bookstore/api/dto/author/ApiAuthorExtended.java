package com.jg.bookstore.api.dto.author;

import lombok.Getter;

import java.util.UUID;

@Getter
public class ApiAuthorExtended extends ApiAuthor {

    private final UUID id;

    public ApiAuthorExtended(final UUID id,
                             final String firstName,
                             final String lastName) {
        super(firstName, lastName);
        this.id = id;
    }
}
