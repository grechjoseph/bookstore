package com.jg.bookstore.service;

import java.math.BigDecimal;
import java.util.Currency;

public interface ForexService {

    void updateRates();

    BigDecimal convert(final BigDecimal baseAmount, final Currency targetCurrency);

}
