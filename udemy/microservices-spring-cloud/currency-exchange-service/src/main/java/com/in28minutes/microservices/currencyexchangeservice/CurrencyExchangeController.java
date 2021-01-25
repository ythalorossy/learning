package com.in28minutes.microservices.currencyexchangeservice;

import java.math.BigDecimal;
import java.util.Objects;

import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class CurrencyExchangeController {
    
    private Environment environment;

    private CurrencyExchangeRepository currencyExchangeRepository;
    
    public CurrencyExchangeController(Environment environment, CurrencyExchangeRepository currencyExchangeRepository) {
        this.environment = environment;
        this.currencyExchangeRepository = currencyExchangeRepository;
    }

    @GetMapping(value="/currency-exchange/from/{from}/to/{to}")
    public CurrencyExchange retrieveExchangeValue(
        @PathVariable String from, 
        @PathVariable String to) {

        // CurrencyExchange currencyExchange = new CurrencyExchange(1000L, from, to, BigDecimal.valueOf(50));

        String port = environment.getProperty("local.server.port");
        
        CurrencyExchange currencyExchange = currencyExchangeRepository.findByFromAndTo(from, to);

        if (Objects.isNull(currencyExchange)) {
            throw new RuntimeException("Unable to find data from %s to %s".formatted(from, to));
        }

        currencyExchange.setEnvironment(port);

        return currencyExchange;
    }

}

