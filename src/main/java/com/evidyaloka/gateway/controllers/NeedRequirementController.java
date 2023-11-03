package com.evidyaloka.gateway.controllers;

import com.evidyaloka.gateway.api.NeedRequirementApi;
import com.evidyaloka.gateway.models.Need.NeedRequirement;
import com.evidyaloka.gateway.repositories.NeedRequirementRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.UUID;


@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
@CrossOrigin(origins = "*")
public class NeedRequirementController implements NeedRequirementApi {

    private final NeedRequirementRepository needRequirementRepository;
    @Override
    public NeedRequirement getNeedRequirementById(String needReqId, Map<String, String> headers) {
        return needRequirementRepository.findById(UUID.fromString(needReqId)).get();
    }
}
