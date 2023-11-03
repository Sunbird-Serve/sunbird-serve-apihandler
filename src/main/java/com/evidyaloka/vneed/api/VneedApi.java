package com.evidyaloka.vneed.api;

import com.evidyaloka.vneed.config.JwtToken;
import com.evidyaloka.vneed.models.VneedResponse;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@RequestMapping("/")
public interface VneedApi {

    @GetMapping(value = "/{needModule}",
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    @JwtToken
    ResponseEntity<VneedResponse> read(
            @PathVariable @NotNull(message = "Field is missing") @NotEmpty(message = "Field cannot be empty") String needModule);

}