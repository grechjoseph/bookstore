package com.jg.bookstore.config;

import com.jg.bookstore.client.forex.ForexClient;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableScheduling
@EnableFeignClients(clients = {ForexClient.class})
@EnableConfigurationProperties({ BookStoreConfigProperties.class })
public class ApplicationConfig {

    /**
     * Needs to be loaded first, to be injected in later beans.
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
