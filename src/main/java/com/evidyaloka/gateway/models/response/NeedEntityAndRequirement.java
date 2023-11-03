package com.evidyaloka.gateway.models.response;

import com.evidyaloka.gateway.models.Need.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Optional;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NeedEntityAndRequirement {
    private Need need;
    private Optional<NeedRequirement> needRequirement;
    private Optional<Occurrence> occurrence;
    private List<TimeSlot> timeSlots;
    private Optional<Entity> entity;
    private Optional<NeedType> needType;
}

