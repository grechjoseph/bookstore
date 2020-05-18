package com.jg.bookstore.config.security;

import com.jg.bookstore.config.security.context.ContextFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

/**
 * Resource Server configuration to provide resource, given a User Access Token.
 * Requires Beans instatiated in AuthorizationServerConfig if separate from the Authorization Server.
 */
@Configuration
@EnableResourceServer
@RequiredArgsConstructor
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    private final ContextFilter contextFilter;
    private final DefaultTokenServices tokenServices;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/oauth/**",
                        "/data/**",
                        "/v2/api-docs",
                        "/configuration/ui",
                        "/swagger-resources/**",
                        "/configuration/security",
                        "/swagger-ui.html",
                        "/webjars/**")
                .permitAll().anyRequest().authenticated();

        // For H2 Console once logged in.
        http.headers().frameOptions().sameOrigin();

        http.addFilterBefore(contextFilter, BasicAuthenticationFilter.class);
    }

    /**
     * Called by MethodSecurityExpressionHandler on Startup. Sets DefaultTokenServices.
     *
     * @param config
     */
    @Override
    public void configure(ResourceServerSecurityConfigurer config) {
        config.tokenServices(tokenServices);
    }
}
