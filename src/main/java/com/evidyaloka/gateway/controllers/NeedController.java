package com.evidyaloka.gateway.controllers;

import com.evidyaloka.gateway.api.NeedApi;
import com.evidyaloka.gateway.models.Need.Entity;
import com.evidyaloka.gateway.models.Need.Need;
import com.evidyaloka.gateway.models.Need.NeedRequirement;
import com.evidyaloka.gateway.models.enums.NeedStatus;
import com.evidyaloka.gateway.models.enums.NominationStatus;
import com.evidyaloka.gateway.models.request.RaiseNeedRequest;
import com.evidyaloka.gateway.models.Need.Nomination;
import com.evidyaloka.gateway.models.response.NeedEntityAndRequirement;
import com.evidyaloka.gateway.repositories.EntityRepository;
import com.evidyaloka.gateway.repositories.NeedRepository;
import com.evidyaloka.gateway.repositories.NeedRequirementRepository;
import com.evidyaloka.gateway.repositories.NominationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
@CrossOrigin(origins = "*")
public class NeedController implements NeedApi {

    private final NeedRepository needRepository;
    private final NeedRequirementRepository needRequirementRepository;
    private final NominationRepository nominationRepository;
    private final EntityRepository entityRepository;

    @Override
    public ResponseEntity<Need> getNeedById(String needId, Map<String, String> headers) {
        return ResponseEntity.ok(needRepository.findById(UUID.fromString(needId)).get());
    }

    @Override
    public Page<NeedEntityAndRequirement> getAllNeeds(Integer page, Integer size, NeedStatus status, Map<String, String> headers) {
        Page<Need> needs = needRepository.findAll(Example.of(
                Need.builder()
                        .status(status)
                        .build()
        ), PageRequest.of(page, size));

        return needs.map(need -> {
            Optional<NeedRequirement> needRequirement = needRequirementRepository.findById(UUID.fromString(need.getRequirementId()));
            Optional<Entity> entity = entityRepository.findById(UUID.fromString(need.getEntityId()));

            return NeedEntityAndRequirement.builder()
                    .need(need)
                    .needRequirement(needRequirement)
                    .entity(entity)
                    .build();
        });
    }

    @Override
    public Page<Need> getAllNeedsForUser(String userId, Integer page, Integer size, NeedStatus status, Map<String, String> headers) {
        return needRepository.findAllByUserIdAndStatus(userId, status, PageRequest.of(page, size));
    }

    @Override
    public Page<Need> getAllNeedsForNeedType(String needTypeId, Integer page, Integer size, NeedStatus status, Map<String, String> headers) {
        return needRepository.findAllByNeedTypeIdAndStatus(needTypeId, status, PageRequest.of(page, size));
    }

    @Override
    public Need createNeed(RaiseNeedRequest request, Map<String, String> headers) {
        String requirementId = request.needRequest.getRequirementId();

        if (requirementId == null) {
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

            requirementId = needRequirement.getId().toString();
        }

        return needRepository.save(
                Need.builder()
                        .needPurpose(request.needRequest.getNeedPurpose())
                        .needTypeId(request.needRequest.getNeedTypeId())
                        .entityId(request.needRequest.getEntityId())
                        .description(request.needRequest.getDescription())
                        .requirementId(requirementId)
                        .userId(request.needRequest.getUserId())
                        .status(request.needRequest.getStatus())
                        .name(request.needRequest.getName())
                        .build()
        );
    }

    @Override
    public Nomination nominateNeed(String needId, String userId, Map<String, String> headers) {
        Need need = needRepository.findById(UUID.fromString(needId)).get();
        Nomination nomination = nominationRepository.save(
                Nomination.builder()
                        .nominationStatus(NominationStatus.Nominated)
                        .nominatedUserId(userId)
                        .needId(needId)
                        .build()
        );

        need.setStatus(NeedStatus.Nominated);
        needRepository.save(need);
        return nomination;
    }

    @Override
    public Nomination updateNeed(String userId, String nominationId, NominationStatus status, Map<String, String> headers) {
        Nomination nomination = nominationRepository.findById(UUID.fromString(nominationId)).get();
        Need need = needRepository.findById(UUID.fromString(nomination.getNeedId())).get();

        nomination.setNominationStatus(status);
        need.setStatus(NeedStatus.valueOf(status.name()));
        needRepository.save(need);
        return nominationRepository.save(nomination);
    }

    @Override
    public List<Nomination> getAllNominations(String needId, Map<String, String> headers) {
        return nominationRepository.findAllByNeedId(needId);
    }

    @Override
    public List<Nomination> getAllNominationsByStatus(String needId, NominationStatus status, Map<String, String> headers) {
        return nominationRepository.findAllByNeedIdAndNominationStatus(needId, status);
    }

    @Override
    public List<Nomination> getAllNominationForUser(String nominatedUserId, Integer page, Integer size, Map<String, String> headers) {
        return nominationRepository.findAllByNominatedUserId(nominatedUserId);
    }
}
