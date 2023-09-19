package com.evidyaloka.gateway.models.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateNeedTypeRequest {

    public NeedTypeRequest needTypeRequest;
    public NeedRequirementRequest needRequirementRequest;
}
