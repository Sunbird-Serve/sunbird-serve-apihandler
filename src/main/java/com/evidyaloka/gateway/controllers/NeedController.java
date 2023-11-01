package com.evidyaloka.gateway.controllers;

import com.evidyaloka.gateway.api.NeedApi;
import com.evidyaloka.gateway.models.Need.*;
import com.evidyaloka.gateway.models.enums.NeedDeliverableStatus;
import com.evidyaloka.gateway.models.enums.NeedStatus;
import com.evidyaloka.gateway.models.enums.NominationStatus;
import com.evidyaloka.gateway.models.request.RaiseNeedRequest;
import com.evidyaloka.gateway.models.response.NeedEntityAndRequirement;
import com.evidyaloka.gateway.repositories.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import java.time.*;
import java.util.*;

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
@CrossOrigin(origins = "*")
public class NeedController implements NeedApi {

    private final NeedRepository needRepository;
    private final NeedRequirementRepository needRequirementRepository;
    private final NominationRepository nominationRepository;
    private final EntityRepository entityRepository;
    private final NeedTypeRepository needTypeRepository;
    private final OccurrenceRepository occurrenceRepository;
    private final NeedDeliverableRepository needDeliverableRepository;
    private final NeedPlanRepository needPlanRepository;
    private final TimeSlotRepository timeSlotRepository;

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
            try {
                Optional<NeedRequirement> needRequirement = needRequirementRepository.findById(UUID.fromString(need.getRequirementId()));
                Optional<Entity> entity = entityRepository.findById(UUID.fromString(need.getEntityId()));
                Optional<NeedType> needType = needTypeRepository.findById((UUID.fromString(need.getNeedTypeId())));

                Optional<Occurrence> occurrence = Optional.empty();
                List<TimeSlot> slots = List.of();
                if (needRequirement.isPresent()) {
                    String occurrenceId = needRequirement.get().getOccurrenceId();
                    if (occurrenceId != null) {
                        occurrence = occurrenceRepository.findById(UUID.fromString(occurrenceId));
                        slots = timeSlotRepository.findByOccurrenceId(occurrenceId);
                    }
                }

                return NeedEntityAndRequirement.builder()
                        .need(need)
                        .needRequirement(needRequirement)
                        .occurrence(occurrence)
                        .timeSlots(slots)
                        .entity(entity)
                        .needType(needType)
                        .build();
            } catch (Exception e) {
                return null;
            }
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
        String requirementId = request.getNeedRequest().getRequirementId();

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

        if (requirementId == null) {
            NeedRequirement needRequirement = needRequirementRepository.save(
                    NeedRequirement.builder()
                            .priority(request.getNeedRequirementRequest().getPriority())
                            .skillDetails(request.getNeedRequirementRequest().getSkillDetails())
                            .occurrenceId(occurrence.getId().toString())
                            .volunteersRequired(request.getNeedRequirementRequest().getVolunteersRequired())
                            .build()
            );

            requirementId = needRequirement.getId().toString();
        }

        return needRepository.save(
                Need.builder()
                        .needPurpose(request.getNeedRequest().getNeedPurpose())
                        .needTypeId(request.getNeedRequest().getNeedTypeId())
                        .entityId(request.getNeedRequest().getEntityId())
                        .description(request.getNeedRequest().getDescription())
                        .requirementId(requirementId)
                        .userId(request.getNeedRequest().getUserId())
                        .status(request.getNeedRequest().getStatus())
                        .name(request.getNeedRequest().getName())
                        .build()
        );
    }

    @Override
    public Nomination nominateNeed(String needId, String userId, Map<String, String> headers) {
        Need need = needRepository.findById(UUID.fromString(needId)).get();

        NeedRequirement needRequirement = needRequirementRepository.findById(UUID.fromString(need.getRequirementId())).get();
        Nomination nomination = nominationRepository.save(
                Nomination.builder()
                        .nominationStatus(NominationStatus.Nominated)
                        .nominatedUserId(userId)
                        .needId(needId)
                        .build()
        );

        NeedPlan needPlan = needPlanRepository.save(
                NeedPlan.builder()
                        .name(need.getName())
                        .assignedUserId(userId)
                        .needId(needId)
                        .occurrenceId(needRequirement.getOccurrenceId())
                        .status(need.getStatus())
                        .build()
        );

        Optional<Occurrence> occurrence = occurrenceRepository.findById(UUID.fromString(needRequirement.getOccurrenceId()));
        if (occurrence.isPresent()) {
            LocalDate startDate = occurrence.get().getStartDate().atZone(ZoneId.of("Asia/Kolkata")).toLocalDate();
            LocalDate endDate = occurrence.get().getEndDate().atZone(ZoneId.of("Asia/Kolkata")).toLocalDate();
            List<DayOfWeek> days = Arrays.stream(occurrence.get().getDays().split(",")).map((day) -> DayOfWeek.valueOf(day.trim().toUpperCase())).toList();

            List<LocalDate> deliverableDates = startDate.datesUntil(endDate, Period.ofDays(1))
                    .filter(localDate ->  days.contains(localDate.getDayOfWeek()))
                    .toList();

            for(LocalDate date: deliverableDates) {
                needDeliverableRepository.save(
                        NeedDeliverable.builder()
                                .needPlanId(needPlan.getId().toString())
                                .deliverableDate(date)
                                .status(NeedDeliverableStatus.NotStarted)
                                .build()
                );
            }
        }


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
