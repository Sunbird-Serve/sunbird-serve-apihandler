package com.evidyaloka.gateway.models.response;

import com.evidyaloka.gateway.models.Need.Entity;
import com.evidyaloka.gateway.models.Need.Need;
import com.evidyaloka.gateway.models.Need.NeedRequirement;
import com.evidyaloka.gateway.models.Need.Nomination;
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
    private Optional<Entity> entity;
}
