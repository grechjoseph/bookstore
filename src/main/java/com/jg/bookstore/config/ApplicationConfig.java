package com.jg.bookstore.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({ BookStoreConfigProperties.class })
public class ApplicationConfig {
}
