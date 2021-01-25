package com.in28minutes.microservices.limitservice.controller;

import com.in28minutes.microservices.limitservice.bean.Limits;
import com.in28minutes.microservices.limitservice.configuration.Configuration;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LimitsController {
    
    
    private Configuration configuration;

    public LimitsController(Configuration configuration) {
        this.configuration = configuration;
    }
    

    @GetMapping("/limits")
    public Limits retrieveLimits() {
        return new Limits(configuration.getMinimum(), configuration.getMaximum());
    }

}
