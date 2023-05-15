package com.evidyaloka.vneed.controllers;


import com.evidyaloka.vneed.api.VneedApi;
import com.evidyaloka.vneed.dao.VneedRepository;
import com.evidyaloka.vneed.models.VneedResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class VneedApiController implements VneedApi {

    private final VneedRepository vneedRepository;

    @Override
    public ResponseEntity<VneedResponse> read(String needModule, Map<String, String> headers) {
        return null;
    }
}