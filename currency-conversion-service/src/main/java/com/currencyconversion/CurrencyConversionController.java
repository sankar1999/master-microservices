package com.currencyconversion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.HashMap;

@RestController
public class CurrencyConversionController {

    @Autowired
    private CurrencyExchangeProxy proxy;

    @GetMapping("/currency-conversion/from/{from}/to/{to}/quantity/{quantity}")
    public CurrencyConversion calculateCurrencyConversion(
            @PathVariable String from,
            @PathVariable String to,
            @PathVariable BigDecimal quantity

            ) {

        HashMap<String, String> uriVariables = new HashMap<>();
        uriVariables.put("from", from);
        uriVariables.put("to", to);

        String uri = "http://localhost:8000/currency-exchange/from/{from}/to/{to}";

        ResponseEntity<CurrencyConversion> responseEntity =
                new RestTemplate().getForEntity(uri, CurrencyConversion.class,
                        uriVariables);

        CurrencyConversion currencyConversionResponse = responseEntity.getBody();

        CurrencyConversion currencyConversion = new CurrencyConversion(
                currencyConversionResponse.getId(),
                from, to, quantity,
                currencyConversionResponse.getConversionMultiple(),
                quantity.multiply(currencyConversionResponse.getConversionMultiple()),
                currencyConversionResponse.getEnvironment() + " "+ "Rest Template");

        return currencyConversion;
    }


    @GetMapping("/currency-conversion-feign/from/{from}/to/{to}/quantity/{quantity}")
    public CurrencyConversion calculateCurrencyConversionFeign(
            @PathVariable String from,
            @PathVariable String to,
            @PathVariable BigDecimal quantity

    ) {



        CurrencyConversion currencyConversionResponse = proxy
                .retrieveExchangeValue(from, to);

        CurrencyConversion currencyConversion = new CurrencyConversion(
                currencyConversionResponse.getId(),
                from, to, quantity,
                currencyConversionResponse.getConversionMultiple(),
                quantity.multiply(currencyConversionResponse.getConversionMultiple()),
                currencyConversionResponse.getEnvironment() + " "+ "feign");

        return currencyConversion;
    }
}
