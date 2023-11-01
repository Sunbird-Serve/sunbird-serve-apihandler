package com.evidyaloka.gateway.models.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TimeSlotRequest {

    private Instant startTime;
    private Instant endTime;
    private String day;
}
