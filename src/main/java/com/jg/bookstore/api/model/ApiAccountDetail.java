package com.jg.bookstore.api.model;

import com.jg.bookstore.domain.validation.Password;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Data
@ApiModel(value = "Account Details model.")
public class ApiAccountDetail {

    @Email(message = "Invalid email.")
    @ApiModelProperty(value = "User's email address.", example = "john@mail.com" )
    private String email;

    @NotEmpty(message = "Password is required.")
    @Password(message = "Password must be 8 to 16 characters long, and contain a mix of lowercase, uppercase, and special characters.")
    private String password;

}
