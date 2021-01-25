package com.in28minutes.microservices.currencyexchangeservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;


@RestController
public class CircuitBreakerController {
    
    private Logger logger = LoggerFactory.getLogger(CircuitBreakerController.class);

    @GetMapping(value="/sample-api")
    // @Retry(name  = "sample-api", fallbackMethod = "hardCodeRsponse")
    // @CircuitBreaker(name  = "default", fallbackMethod = "hardCodeRsponse")
    // @RateLimiter(name = "default")
    @Bulkhead(name = "desample-api")
    public String sampleApi() {
        logger.info("Sample Api called");
        // ResponseEntity<String> forEntity = new RestTemplate().getForEntity("http://localhost:8080/some-dummy-url", String.class);
        // return forEntity.getBody();

        return "samle api";
    }
    

    public String hardCodeRsponse(Exception exception) {
        return "fallback-response";
    }
}
