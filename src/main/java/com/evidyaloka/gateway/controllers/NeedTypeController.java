package com.evidyaloka.gateway.controllers;

import com.evidyaloka.gateway.api.NeedTypeApi;
import com.evidyaloka.gateway.models.Need.NeedRequirement;
import com.evidyaloka.gateway.models.Need.NeedType;
import com.evidyaloka.gateway.models.enums.NeedTypeStatus;
import com.evidyaloka.gateway.models.request.CreateNeedTypeRequest;
import com.evidyaloka.gateway.repositories.NeedRequirementRepository;
import com.evidyaloka.gateway.repositories.NeedTypeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.UUID;

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
@CrossOrigin(origins = "*")
public class NeedTypeController implements NeedTypeApi {

    private final NeedTypeRepository needTypeRepository;
    private final NeedRequirementRepository needRequirementRepository;

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
        NeedRequirement needRequirement = needRequirementRepository.save(
                NeedRequirement.builder()
                        .frequency(request.needRequirementRequest.getFrequency())
                        .priority(request.needRequirementRequest.getPriority())
                        .skillDetails(request.needRequirementRequest.getSkillDetails())
                        .volunteersRequired(request.needRequirementRequest.getVolunteersRequired())
                        .startDate(request.needRequirementRequest.getStartDate())
                        .endDate(request.needRequirementRequest.getEndDate())
                        .build()
        );

        return needTypeRepository.save(
                NeedType.builder()
                        .name(request.needTypeRequest.getName())
                        .requirementId(needRequirement.getId().toString())
                        .description(request.needTypeRequest.getDescription())
                        .onboardingId(request.needTypeRequest.getOnboardingId())
                        .userId(request.needTypeRequest.getUserId())
                        .taxonomyId(request.needTypeRequest.getTaxonomyId())
                        .taskType(request.needTypeRequest.getTaskType())
                        .status(request.needTypeRequest.getStatus())
                        .build()
        );
    }
}
