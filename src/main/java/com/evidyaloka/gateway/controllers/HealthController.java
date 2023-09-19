package com.evidyaloka.gateway.controllers;

import com.evidyaloka.gateway.api.HealthApi;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class HealthController implements HealthApi {

    @Override
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Service is up!");
    }
}
