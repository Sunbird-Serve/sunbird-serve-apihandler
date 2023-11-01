package com.evidyaloka.gateway.models.response;

import com.evidyaloka.gateway.models.Need.NeedPlan;
import com.evidyaloka.gateway.models.Need.Occurrence;
import com.evidyaloka.gateway.models.Need.TimeSlot;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NeedPlanResponse {
    private NeedPlan plan;
    private Occurrence occurrence;
    private List<TimeSlot> timeSlots;
}
