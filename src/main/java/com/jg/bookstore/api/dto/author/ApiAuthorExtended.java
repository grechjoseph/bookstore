package com.jg.bookstore.api.dto.author;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

import java.util.UUID;

@Getter
@ApiModel(value = "Object representing an Author being read.")
public class ApiAuthorExtended extends ApiAuthor {

    @ApiModelProperty(value = "Author's ID.", example = "064f4cfb-5bcc-44e5-96cd-780830586eb8" )
    private final UUID id;

    public ApiAuthorExtended(final UUID id,
                             final String firstName,
                             final String lastName) {
        super(firstName, lastName);
        this.id = id;
    }
}
