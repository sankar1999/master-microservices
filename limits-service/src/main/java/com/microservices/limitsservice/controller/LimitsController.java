package com.microservices.limitsservice.controller;

import com.microservices.limitsservice.bean.Limits;
import com.microservices.limitsservice.config.LimitConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LimitsController {

    @Autowired
    private LimitConfiguration limitConfiguration;

    @GetMapping("/limits")
    public Limits getLimits() {
        return new Limits(limitConfiguration.getMinimum(),
                limitConfiguration.getMaximum());
    }
}
