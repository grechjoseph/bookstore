package com.jg.bookstore.config.context;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Currency;
import java.util.Objects;

@Component
public class ContextFilter extends OncePerRequestFilter {

    private static final String DISPLAY_CURRENCY = "display-currency";

    @Override
    protected void doFilterInternal(final HttpServletRequest request, final HttpServletResponse response, final FilterChain filterChain) throws ServletException, IOException {
        final Context context = new Context();
        if (Objects.nonNull(request.getHeader(DISPLAY_CURRENCY))) {
            context.setDisplayCurrency(Currency.getInstance(request.getHeader(DISPLAY_CURRENCY)));
        }
        ContextHolder.setContext(context);
        filterChain.doFilter(request, response);
    }
}
