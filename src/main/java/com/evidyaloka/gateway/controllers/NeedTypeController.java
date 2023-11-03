package com.evidyaloka.gateway.controllers;

import com.evidyaloka.gateway.api.NeedTypeApi;
import com.evidyaloka.gateway.models.Need.NeedRequirement;
import com.evidyaloka.gateway.models.Need.NeedType;
import com.evidyaloka.gateway.models.Need.Occurrence;
import com.evidyaloka.gateway.models.Need.TimeSlot;
import com.evidyaloka.gateway.models.enums.NeedTypeStatus;
import com.evidyaloka.gateway.models.request.CreateNeedTypeRequest;
import com.evidyaloka.gateway.repositories.NeedRequirementRepository;
import com.evidyaloka.gateway.repositories.NeedTypeRepository;
import com.evidyaloka.gateway.repositories.OccurrenceRepository;
import com.evidyaloka.gateway.repositories.TimeSlotRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
@CrossOrigin(origins = "*")
public class NeedTypeController implements NeedTypeApi {

    private final NeedTypeRepository needTypeRepository;
    private final NeedRequirementRepository needRequirementRepository;

    private final OccurrenceRepository occurrenceRepository;
    private final TimeSlotRepository timeSlotRepository;

    @Override
    public NeedType getNeedTypeById(String needTypeId, Map<String, String> headers) {
        return needTypeRepository.findById(UUID.fromString(needTypeId)).get();
    }

    @Override
    public Page<NeedType> getAllNeedTypes(Integer page, Integer size, NeedTypeStatus status, Map<String, String> headers) {
        return needTypeRepository.findAllByStatus(status, PageRequest.of(page, size));
    }

    @Override
    public NeedType createNeedType(CreateNeedTypeRequest request, Map<String, String> headers) {
        Occurrence occurrence = occurrenceRepository.save(
                Occurrence.builder()
                        .days(request.getNeedRequirementRequest().getOccurrence().getDays())
                        .frequency(request.getNeedRequirementRequest().getOccurrence().getFrequency())
                        .startDate(request.getNeedRequirementRequest().getOccurrence().getStartDate())
                        .endDate(request.getNeedRequirementRequest().getOccurrence().getEndDate())
                        .build()
        );

        timeSlotRepository.saveAll(
                request.getNeedRequirementRequest()
                        .getOccurrence().getTimeSlots()
                        .stream().map(req -> TimeSlot.builder()
                                .day(req.getDay())
                                .startTime(req.getStartTime())
                                .endTime(req.getEndTime())
                                .occurrenceId(occurrence.getId().toString())
                                .build()).toList()
        );

        NeedRequirement needRequirement = needRequirementRepository.save(
                NeedRequirement.builder()
                        .priority(request.getNeedRequirementRequest().getPriority())
                        .skillDetails(request.getNeedRequirementRequest().getSkillDetails())
                        .volunteersRequired(request.getNeedRequirementRequest().getVolunteersRequired())
                        .occurrenceId(occurrence.getId().toString())
                        .build()
        );

        return needTypeRepository.save(
                NeedType.builder()
                        .name(request.getNeedTypeRequest().getName())
                        .requirementId(needRequirement.getId().toString())
                        .description(request.getNeedTypeRequest().getDescription())
                        .onboardingId(request.getNeedTypeRequest().getOnboardingId())
                        .userId(request.getNeedTypeRequest().getUserId())
                        .taxonomyId(request.getNeedTypeRequest().getTaxonomyId())
                        .taskType(request.getNeedTypeRequest().getTaskType())
                        .status(request.getNeedTypeRequest().getStatus())
                        .build()
        );
    }
}
