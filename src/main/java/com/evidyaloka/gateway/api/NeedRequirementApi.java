package com.evidyaloka.gateway.api;

import com.evidyaloka.gateway.models.Need.NeedRequirement;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@RequestMapping("/need-requirement")
public interface NeedRequirementApi {
    @Operation(summary = "Get a particular need requirement by its primary key", description = "Get Need Requirement by Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully fetched Need Requirement", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)),
            @ApiResponse(responseCode = "400", description = "Bad Input"),
            @ApiResponse(responseCode = "500", description = "Server Error")}
    )
    @GetMapping(value = "/{needReqId}",
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    NeedRequirement getNeedRequirementById(
            @PathVariable @NotNull(message = "Field is missing") @NotEmpty(message = "Field cannot be empty") String needReqId,
            @Parameter() @RequestHeader Map<String, String> headers);
}
