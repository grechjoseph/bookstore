package com.jg.bookstore.api.model.author;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@ApiModel(value = "Object representing an Author being created or updated.")
public class ApiAuthor {


    @NotEmpty(message = "First Name must be between 2 and 20 characters.")
    @Size(min = 2, max = 20, message = "First Name must be between 2 and 20 characters.")
    @ApiModelProperty(value = "Author's first name.", example = "John" )
    private final String firstName;

    @NotEmpty(message = "Last Name must be between 2 and 20 characters.")
    @Size(min = 2, max = 20, message = "Last Name must be between 2 and 20 characters.")
    @ApiModelProperty(value = "Author's last name.", example = "Doe" )
    private final String lastName;

}
