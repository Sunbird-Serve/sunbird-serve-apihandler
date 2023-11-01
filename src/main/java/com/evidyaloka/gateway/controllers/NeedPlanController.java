package com.evidyaloka.gateway.controllers;

import com.evidyaloka.gateway.api.NeedPlanApi;
import com.evidyaloka.gateway.models.Need.NeedPlan;
import com.evidyaloka.gateway.models.Need.Occurrence;
import com.evidyaloka.gateway.models.Need.TimeSlot;
import com.evidyaloka.gateway.models.response.NeedPlanResponse;
import com.evidyaloka.gateway.repositories.NeedPlanRepository;
import com.evidyaloka.gateway.repositories.OccurrenceRepository;
import com.evidyaloka.gateway.repositories.TimeSlotRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
@CrossOrigin(origins = "*")
public class NeedPlanController implements NeedPlanApi {

    private final NeedPlanRepository needPlanRepository;
    private final OccurrenceRepository occurrenceRepository;
    private final TimeSlotRepository timeSlotRepository;

    @Override
    public ResponseEntity<List<NeedPlanResponse>> getNeedPlanByNeedId(String needId, Map<String, String> headers) {
        List<NeedPlan> needPlans = needPlanRepository.findByNeedId(needId);
        List<NeedPlanResponse> response = needPlans.stream().map(plan -> {
            Occurrence occurrence = occurrenceRepository.findById(UUID.fromString(plan.getOccurrenceId())).get();
            List<TimeSlot> slots = timeSlotRepository.findByOccurrenceId(plan.getOccurrenceId());
            return NeedPlanResponse.builder()
                    .plan(plan)
                    .occurrence(occurrence)
                    .timeSlots(slots)
                    .build();
        }).toList();

        return ResponseEntity.ok(response);
    }
}
