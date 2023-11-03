package com.evidyaloka.gateway.controllers;

import com.evidyaloka.gateway.api.EntityApi;
import com.evidyaloka.gateway.models.Need.Entity;
import com.evidyaloka.gateway.models.enums.EntityStatus;
import com.evidyaloka.gateway.repositories.EntityRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.UUID;

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
@CrossOrigin(origins = "*")
public class EntityController implements EntityApi {

    private final EntityRepository entityRepository;

    @Override
    public Page<Entity> getAllEntities(Integer page, Integer size, EntityStatus status, Map<String, String> headers) {
        return entityRepository.findAllByStatus(status, PageRequest.of(page, size));
    }
    @Override
    public ResponseEntity<Entity> getEntityById(String entityId, Map<String, String> headers) {
        return ResponseEntity.ok(entityRepository.findById(UUID.fromString(entityId)).get());
    }
}
