package com.jg.bookstore.api.model;

import com.jg.bookstore.domain.validation.PhoneNumber;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@ApiModel(value = "User Details model.")
public class ApiUserDetail {

    @NotEmpty(message = "First Name must be between 2 and 20 characters.")
    @Size(min = 2, max = 20, message = "First Name must be between 2 and 20 characters.")
    @ApiModelProperty(value = "Author's first name.", example = "John" )
    private String firstName;

    @NotEmpty(message = "Last Name must be between 2 and 20 characters.")
    @Size(min = 2, max = 20, message = "Last Name must be between 2 and 20 characters.")
    @ApiModelProperty(value = "Author's last name.", example = "Doe" )
    private String lastName;

    @NotNull(message = "Phone number is required.")
    @PhoneNumber(message = "Invalid phone number.")
    @ApiModelProperty(value = "Mobile number.", example = "+35679797979")
    private String mobileNumber;

}
