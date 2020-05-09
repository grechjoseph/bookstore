package com.jg.bookstore.client.forex;

import lombok.Data;

import java.util.Currency;
import java.util.Date;
import java.util.Map;

@Data
public class RatesForBaseResponse {

    private final Map<Currency, Double> rates;
    private final Currency base;
    private final Date date;

}
