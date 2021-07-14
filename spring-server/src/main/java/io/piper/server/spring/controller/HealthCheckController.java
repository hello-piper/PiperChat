package io.piper.server.spring.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/health")
public class HealthCheckController {
    @RequestMapping
    public Object healthCheck() {
        return "Health Check Success";
    }
}
