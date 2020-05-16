package com.jg.bookstore.config.security.context;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

import static com.jg.bookstore.config.security.CustomJwtTokenConverter.*;

/**
 * A OncePerRequestFilter extension that, as the name implies, execute once per request.
 */
@Slf4j
@Component
public class ContextFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest,
                                    HttpServletResponse httpServletResponse,
                                    FilterChain filterChain) {
        try {
            Context context = new Context();
            populateContext(context);
            ContextHolder.set(context);
            filterChain.doFilter(httpServletRequest, httpServletResponse);
        } catch (Exception ex) {
            log.error(ex.getMessage());
        } finally {
            ContextHolder.clear();
        }
    }

    private void populateContext(Context context) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.getDetails() instanceof OAuth2AuthenticationDetails) {
            OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails) authentication.getDetails();
            if (details != null && details.getDecodedDetails() != null) {
                Map<String, Object> decodedDetails = (Map<String, Object>) details.getDecodedDetails();

                context.setUsername((String) decodedDetails.get(USERNAME));
                context.setUserId(UUID.fromString(decodedDetails.get(USER_ID).toString()));
                context.setPermissions((List<String>) decodedDetails.get(AUTHORITY));

                context.setDisplayCurrency(Objects.nonNull(decodedDetails.get(DISPLAY_CURRENCY)) ?
                        Currency.getInstance(decodedDetails.get(DISPLAY_CURRENCY).toString())
                        : null);
            }
        }
    }
}
