package com.jg.bookstore.api.controller.advice;

import com.jg.bookstore.api.model.ApiBook;
import com.jg.bookstore.api.model.ApiOrderEntry;
import com.jg.bookstore.api.model.ApiPurchaseOrder;
import com.jg.bookstore.config.context.ContextHolder;
import com.jg.bookstore.service.ForexService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.Currency;
import java.util.List;
import java.util.Objects;

/**
 * Controller Advice to handle Forex when a display currency is provided. This handles populating the Api object with
 * the converted Price for GUI only, when applicable.
 */
@ControllerAdvice
@RequiredArgsConstructor
public class ForexAdapter implements ResponseBodyAdvice<Object> {

    private final ForexService forexService;

    @Override
    public boolean supports(final MethodParameter methodParameter, final Class aClass) {
        return Objects.nonNull(getDisplayCurrency()) && List.of(ApiPurchaseOrder.class, ApiOrderEntry.class, ApiBook.class)
                .contains(methodParameter.getParameterType());
    }

    @Override
    public Object beforeBodyWrite(final Object responseBody,
                                  final MethodParameter methodParameter,
                                  final MediaType mediaType,
                                  final Class<? extends HttpMessageConverter<?>> aClass,
                                  final ServerHttpRequest serverHttpRequest,
                                  final ServerHttpResponse serverHttpResponse) {

        checkIfApiPurchaseOrder(responseBody);
        checkIfApiBook(responseBody);

        return responseBody;

    }

    private void checkIfApiBook(final Object responseBody) {
        if (ApiBook.class.equals(responseBody.getClass())) {
            ((ApiBook)responseBody).setConvertedPrice(forexService.convert(((ApiBook)responseBody).getPrice(), getDisplayCurrency()));
        }
    }

    private void checkIfApiPurchaseOrder(final Object responseBody) {
        if (ApiPurchaseOrder.class.equals(responseBody.getClass())) {
            ((ApiPurchaseOrder)responseBody).setConvertedPrice(forexService.convert(((ApiPurchaseOrder)responseBody).getTotalPrice(), getDisplayCurrency()));
            ((ApiPurchaseOrder)responseBody).getOrderEntries().stream()
                    .filter(apiOrderEntry -> Objects.nonNull(apiOrderEntry.getFinalUnitPrice()))
                    .forEach(apiOrderEntry -> apiOrderEntry.setConvertedFinalUnitPrice(
                    forexService.convert(apiOrderEntry.getFinalUnitPrice(), getDisplayCurrency())
            ));
        }
    }

    private Currency getDisplayCurrency() {
        return ContextHolder.getContext().getDisplayCurrency();
    }

}
