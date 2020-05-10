package com.jg.bookstore.config.context;

import com.jg.bookstore.config.BookStoreConfigProperties;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class ContextFilter extends OncePerRequestFilter {

    private static final String DISPLAY_CURRENCY = "display-currency";

    private final BookStoreConfigProperties configProperties;

    @Override
    protected void doFilterInternal(final HttpServletRequest request, final HttpServletResponse response, final FilterChain filterChain) throws ServletException, IOException {
        final Context context = new Context();
        if (Objects.nonNull(request.getHeader(DISPLAY_CURRENCY))) {
            try {
                final Currency displayCurrencyRequest = Currency.getInstance(request.getHeader(DISPLAY_CURRENCY).toUpperCase());

                if(!displayCurrencyRequest.equals(configProperties.getBaseCurrency())) {
                    context.setDisplayCurrency(displayCurrencyRequest);
                }
            } catch (final IllegalArgumentException e) {
                context.setDisplayCurrency(null);
            }
        }
        ContextHolder.setContext(context);
        filterChain.doFilter(request, response);
    }
}
