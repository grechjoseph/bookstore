package com.jg.bookstore.config.security.permissions;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;

/**
 * Injects our custom expression handler into the security context so that Spring will know about our custom methods.
 */
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class MethodSecurityConfig extends GlobalMethodSecurityConfiguration {

    /**
     * Executed on Startup. Overrides createExpressionHandler to return our CustomMethodSecurityExpressionHandler()
     * @return
     */
    @Override
    protected MethodSecurityExpressionHandler createExpressionHandler() {
        return new CustomSecurityExpressionHandler();
    }
}
