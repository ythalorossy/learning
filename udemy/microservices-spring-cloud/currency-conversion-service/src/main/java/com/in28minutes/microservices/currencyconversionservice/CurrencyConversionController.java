package com.in28minutes.microservices.currencyconversionservice;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class CurrencyConversionController {

    private CurrencyExchangeProxy currencyExchangeProxy;

    public CurrencyConversionController(CurrencyExchangeProxy currencyExchangeProxy) {
        this.currencyExchangeProxy = currencyExchangeProxy;
    }

    @GetMapping(value = "/currency-conversion/from/{from}/to/{to}/quantity/{quantity}")
    public CurrencyConversion calculateCurrencyConversion(@PathVariable String from, @PathVariable String to,
            @PathVariable BigDecimal quantity) {

        Map<String, String> uriVariables = new HashMap<>();
        uriVariables.put("from", from);
        uriVariables.put("to", to);

        ResponseEntity<CurrencyConversion> responseEntity = new RestTemplate().getForEntity(
                "http://localhost:8000/currency-exchange/from/{from}/to/{to}", CurrencyConversion.class,
                uriVariables);

        CurrencyConversion currencionConversion = responseEntity.getBody();

        return new CurrencyConversion(
            currencionConversion.getId(), 
            from, 
            to, 
            quantity, 
            currencionConversion.getConversionMultiple(), 
            currencionConversion.getConversionMultiple().multiply(quantity),
            currencionConversion.getEnvironment());
    }

    @GetMapping(value = "/currency-conversion-feign/from/{from}/to/{to}/quantity/{quantity}")
    public CurrencyConversion calculateCurrencyConversionFeign(@PathVariable String from, @PathVariable String to,
            @PathVariable BigDecimal quantity) {

        Map<String, String> uriVariables = new HashMap<>();
        uriVariables.put("from", from);
        uriVariables.put("to", to);

        CurrencyConversion currencionConversion = currencyExchangeProxy.retrieveExchangeValue(from, to);

        return new CurrencyConversion(
            currencionConversion.getId(), 
            from, 
            to, 
            quantity, 
            currencionConversion.getConversionMultiple(), 
            currencionConversion.getConversionMultiple().multiply(quantity),
            currencionConversion.getEnvironment());
    }



}
