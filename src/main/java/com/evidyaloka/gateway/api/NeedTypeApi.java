package com.evidyaloka.gateway.api;

import com.evidyaloka.gateway.models.Need.NeedType;
import com.evidyaloka.gateway.models.enums.NeedTypeStatus;
import com.evidyaloka.gateway.models.request.CreateNeedTypeRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RequestMapping("/needtype")
public interface NeedTypeApi {

    @Operation(summary = "Get a particular need type by its primary key", description = "Get Need Type by Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully fetched Need Type", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)),
            @ApiResponse(responseCode = "400", description = "Bad Input"),
            @ApiResponse(responseCode = "500", description = "Server Error")}
    )
    @GetMapping(value = "/{needTypeId}",
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    NeedType getNeedTypeById(
            @PathVariable @NotNull(message = "Field is missing") @NotEmpty(message = "Field cannot be empty") String needTypeId,
            @Parameter() @RequestHeader Map<String, String> headers);


    @Operation(summary = "Get list of all need types by their status", description = "Get all need types")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully fetched Need Type list", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)),
            @ApiResponse(responseCode = "400", description = "Bad Input"),
            @ApiResponse(responseCode = "500", description = "Server Error")}
    )
    @GetMapping(value = "/",
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    Page<NeedType> getAllNeedTypes(
            @RequestParam(required = false, defaultValue = "0")  Integer page,
            @RequestParam(required = false, defaultValue = "10")  Integer size,
            @RequestParam(required = true) NeedTypeStatus status,
            @Parameter() @RequestHeader Map<String, String> headers);


    @Operation(summary = "Create new need type with status as New", description = "Add a need type")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created a need type", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)),
            @ApiResponse(responseCode = "400", description = "Bad Input"),
            @ApiResponse(responseCode = "500", description = "Server Error")}
    )
    @PostMapping(value = "/",
            produces = {MediaType.APPLICATION_JSON_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE}
    )
    NeedType createNeedType(
            @RequestBody CreateNeedTypeRequest request,
            @Parameter() @RequestHeader Map<String, String> headers);

}
