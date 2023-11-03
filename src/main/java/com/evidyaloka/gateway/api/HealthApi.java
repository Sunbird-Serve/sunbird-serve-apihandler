package com.evidyaloka.gateway.api;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/")
public interface HealthApi {

    @GetMapping(
            value = "/health",
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    ResponseEntity<String> health();
}