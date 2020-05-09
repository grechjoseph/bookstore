package com.jg.bookstore.api.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.UUID;

@Data
@ApiModel(value = "Author object.")
public class ApiAuthor {

    @ApiModelProperty(value = "Author's ID.", example = "064f4cfb-5bcc-44e5-96cd-780830586eb8" )
    private UUID id;

    @NotEmpty(message = "First Name must be between 2 and 20 characters.")
    @Size(min = 2, max = 20, message = "First Name must be between 2 and 20 characters.")
    @ApiModelProperty(value = "Author's first name.", example = "John" )
    private String firstName;

    @NotEmpty(message = "Last Name must be between 2 and 20 characters.")
    @Size(min = 2, max = 20, message = "Last Name must be between 2 and 20 characters.")
    @ApiModelProperty(value = "Author's last name.", example = "Doe" )
    private String lastName;

}
