package com.evidyaloka.gateway.models.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsersSearchPage {
    private Integer offset;
    private Integer limit;
    private StatusFilter filters;
}
