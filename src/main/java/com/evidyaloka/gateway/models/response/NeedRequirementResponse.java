package com.evidyaloka.gateway.models.response;

import com.evidyaloka.gateway.models.Need.NeedRequirement;
import com.evidyaloka.gateway.models.Need.Occurrence;
import com.evidyaloka.gateway.models.Need.TimeSlot;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Optional;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NeedRequirementResponse {
    private Optional<NeedRequirement> needRequirement;
    private Optional<Occurrence> occurrence;
    private List<TimeSlot> timeSlots;
}
