package com.jg.bookstore.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Currency;

@Data
@ConfigurationProperties(prefix = "bookstore")
public class BookStoreConfigProperties {

    private Currency baseCurrency;

}
