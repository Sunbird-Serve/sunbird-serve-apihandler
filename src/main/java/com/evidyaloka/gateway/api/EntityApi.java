package com.evidyaloka.gateway.api;

import com.evidyaloka.gateway.models.Need.Entity;
import com.evidyaloka.gateway.models.Need.Need;
import com.evidyaloka.gateway.models.enums.EntityStatus;
import com.evidyaloka.gateway.models.enums.NeedStatus;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequestMapping("/entity")
public interface EntityApi {

    @Operation(summary = "Get list of all entities by their status", description = "Get all entities")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully fetched Entity list", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)),
            @ApiResponse(responseCode = "400", description = "Bad Input"),
            @ApiResponse(responseCode = "500", description = "Server Error")}
    )
    @GetMapping(value = "/",
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    Page<Entity> getAllEntities(
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer size,
            @RequestParam(required = true) EntityStatus status,
            @Parameter() @RequestHeader Map<String, String> headers);


    @Operation(summary = "Get a particular entity by entity id", description = "Get Entity by Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully fetched Need", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)),
            @ApiResponse(responseCode = "400", description = "Bad Input"),
            @ApiResponse(responseCode = "500", description = "Server Error")})
    @GetMapping(value = "/{entityId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    ResponseEntity<Entity> getEntityById(
            @PathVariable @NotNull(message = "Field is missing") @NotEmpty(message = "Field cannot be empty") String entityId,
            @Parameter() @RequestHeader Map<String, String> headers);

}
