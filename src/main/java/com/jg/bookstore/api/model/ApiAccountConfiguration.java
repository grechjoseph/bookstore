package com.jg.bookstore.api.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Currency;

@Data
@ApiModel(value = "Account configuration model.")
public class ApiAccountConfiguration {

    @ApiModelProperty(value = "The User's preferred display currency, otherwise the system's base currency will be used.",
            example = "GBP")
    private Currency displayCurrency;

}
