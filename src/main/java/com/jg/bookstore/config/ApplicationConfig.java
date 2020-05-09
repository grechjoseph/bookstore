package com.jg.bookstore.config;

import com.jg.bookstore.client.forex.ForexClient;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
@EnableFeignClients(clients = {ForexClient.class})
@EnableConfigurationProperties({ BookStoreConfigProperties.class })
public class ApplicationConfig {
}
