package com.evidyaloka.gateway.api;

import com.evidyaloka.gateway.models.Need.Need;
import com.evidyaloka.gateway.models.Need.NeedPlan;
import com.evidyaloka.gateway.models.response.NeedPlanResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

@RequestMapping("/need-plan")
public interface NeedPlanApi {

    @Operation(summary = "Get a particular need plan by its need Id", description = "Get Need Plan by need Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully fetched Need", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)),
            @ApiResponse(responseCode = "400", description = "Bad Input"),
            @ApiResponse(responseCode = "500", description = "Server Error")}
    )
    @GetMapping(value = "/{needId}",
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    ResponseEntity<List<NeedPlanResponse>> getNeedPlanByNeedId(
            @PathVariable @NotNull(message = "Field is missing") @NotEmpty(message = "Field cannot be empty") String needId,
            @Parameter() @RequestHeader Map<String, String> headers);
}
