package com.jg.bookstore.client.forex;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Currency;

@FeignClient(name = "forex-client", url = "https://api.exchangeratesapi.io" )
public interface ForexClient {

    @GetMapping(value = "/latest")
    RatesForBaseResponse getForexRatesForBaseCurrency(@RequestParam("base") final Currency baseCurrency);

}
