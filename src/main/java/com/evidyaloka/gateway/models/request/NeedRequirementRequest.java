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
public class NeedRequirementRequest {

    public String skillDetails;
    public String frequency;
    public String volunteersRequired;
    public Instant startDate;
    public Instant endDate;
    public String priority;
}
