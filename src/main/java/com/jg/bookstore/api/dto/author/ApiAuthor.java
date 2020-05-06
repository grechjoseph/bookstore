package com.jg.bookstore.api.dto.author;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Size;

@Data
@ApiModel(value = "Object representing an Author being created or updated.")
public class ApiAuthor {


    @Size(min = 2, max = 20)
    @ApiModelProperty(value = "Author's first name.")
    private final String firstName;

    @Size(min = 2, max = 20)
    @ApiModelProperty(value = "Author's last name.")
    private final String lastName;

}
