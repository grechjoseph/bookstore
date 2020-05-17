package com.jg.bookstore.api.model;

import com.jg.bookstore.domain.enums.AddressType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Enumerated;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import static javax.persistence.EnumType.STRING;

@Data
@ApiModel(value = "Address model.")
public class ApiAddress {

    @NotNull
    @Enumerated(STRING)
    @ApiModelProperty(value = "Address type.", example = "SHIPPING")
    private AddressType addressType;

    @NotEmpty
    @ApiModelProperty(value = "Address Line 1.", example = "4 House Name")
    private String addressLine1;

    @NotEmpty
    @ApiModelProperty(value = "Address Line 12.", example = "Downing Street")
    private String addressLine2;

    @NotEmpty
    @ApiModelProperty(value = "City.", example = "London")
    private String city;

    @NotEmpty
    @ApiModelProperty(value = "Country.", example = "United Kingdom")
    private String country;

    @NotEmpty
    @ApiModelProperty(value = "Post code.", example = "ABC1234")
    private String postCode;

}
