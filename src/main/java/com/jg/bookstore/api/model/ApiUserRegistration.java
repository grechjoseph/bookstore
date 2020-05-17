package com.jg.bookstore.api.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Data
@ApiModel(value = "User Registration model.")
public class ApiUserRegistration {

    @NotNull
    @Valid
    @ApiModelProperty(value = "Account Details.")
    private ApiAccountDetail accountDetails;

    @NotNull
    @Valid
    @ApiModelProperty(value = "User Details.")
    private ApiUserDetail userDetails;

    @NotNull
    @Valid
    @ApiModelProperty(value = "User Addresses.")
    private Set<ApiAddress> addresses;

    @NotNull
    @Valid
    @ApiModelProperty(value = "Account configuration.")
    private ApiAccountConfiguration accountConfiguration;

}
