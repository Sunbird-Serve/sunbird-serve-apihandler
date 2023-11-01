package com.evidyaloka.gateway.controllers;

import com.evidyaloka.gateway.api.NeedDeliverableApi;
import com.evidyaloka.gateway.models.Need.NeedDeliverable;
import com.evidyaloka.gateway.repositories.NeedDeliverableRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
@CrossOrigin(origins = "*")
public class NeedDeliverableController implements NeedDeliverableApi {

    private final NeedDeliverableRepository needDeliverableRepository;

    @Override
    public ResponseEntity<List<NeedDeliverable>> getNeedDeliverablesByPlanId(String needPlanId, Map<String, String> headers) {
        List<NeedDeliverable> needDeliverables = needDeliverableRepository.findByNeedPlanId(needPlanId);
        return ResponseEntity.ok(needDeliverables);
    }
}
