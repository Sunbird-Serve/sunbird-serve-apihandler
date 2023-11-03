package com.evidyaloka.gateway.api;

import com.evidyaloka.gateway.models.Need.NeedDeliverable;
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

@RequestMapping("/need-deliverable")
public interface NeedDeliverableApi {

    @Operation(summary = "Get all need deliverables by its need plan Id", description = "Get Need Deliverables by need plan Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully fetched Need Deliverables", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)),
            @ApiResponse(responseCode = "400", description = "Bad Input"),
            @ApiResponse(responseCode = "500", description = "Server Error")}
    )
    @GetMapping(value = "/{needPlanId}",
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    ResponseEntity<List<NeedDeliverable>> getNeedDeliverablesByPlanId(
            @PathVariable @NotNull(message = "Field is missing") @NotEmpty(message = "Field cannot be empty") String needPlanId,
            @Parameter() @RequestHeader Map<String, String> headers);
}

