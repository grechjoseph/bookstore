package com.jg.bookstore.service.impl;

import com.jg.bookstore.client.forex.ForexClient;
import com.jg.bookstore.client.forex.RatesForBaseResponse;
import com.jg.bookstore.config.BookStoreConfigProperties;
import com.jg.bookstore.domain.exception.BaseException;
import com.jg.bookstore.service.ForexService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Currency;
import java.util.HashMap;
import java.util.Map;

import static com.jg.bookstore.domain.exception.ErrorCode.CURRENCY_NOT_FOUND;

@Slf4j
@Service
@RequiredArgsConstructor
public class ForexServiceImpl implements ForexService {

    private final BookStoreConfigProperties configProperties;
    private final ForexClient forexClient;
    private final Map<Currency, Double> conversionRates = new HashMap<>();

    @Override
    @EventListener(ApplicationReadyEvent.class)
    @Scheduled(cron = "${bookstore.forex-update-cron}")
    public void updateRates() {
        log.info("Updating currency rates for base currency [{}].", configProperties.getBaseCurrency());
        final RatesForBaseResponse forexRatesForBaseCurrency = forexClient.getForexRatesForBaseCurrency(configProperties.getBaseCurrency());
        forexRatesForBaseCurrency.getRates().forEach(conversionRates::put);
    }

    @Override
    public BigDecimal convert(final BigDecimal baseAmount, final Currency targetCurrency) {
        return baseAmount.multiply(BigDecimal.valueOf(getRateForCurrency(targetCurrency)))
                .setScale(2, RoundingMode.HALF_UP);
    }

    private Double getRateForCurrency(final Currency targetCurrency) {
        if (!conversionRates.containsKey(targetCurrency)) {
            throw new BaseException(CURRENCY_NOT_FOUND);
        }

        return conversionRates.get(targetCurrency);
    }
}
