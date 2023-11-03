package com.evidyaloka.gateway.api;

import com.evidyaloka.gateway.models.Need.Need;
import com.evidyaloka.gateway.models.enums.NeedStatus;
import com.evidyaloka.gateway.models.enums.NominationStatus;
import com.evidyaloka.gateway.models.request.RaiseNeedRequest;
import com.evidyaloka.gateway.models.Need.Nomination;
import com.evidyaloka.gateway.models.response.NeedEntityAndRequirement;
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

@RequestMapping("/need")
public interface NeedApi {

    @Operation(summary = "Get a particular need by its primary key", description = "Get Need by Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully fetched Need", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)),
            @ApiResponse(responseCode = "400", description = "Bad Input"),
            @ApiResponse(responseCode = "500", description = "Server Error")}
    )
    @GetMapping(value = "/{needId}",
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    ResponseEntity<Need> getNeedById(
            @PathVariable @NotNull(message = "Field is missing") @NotEmpty(message = "Field cannot be empty") String needId,
            @Parameter() @RequestHeader Map<String, String> headers);


    @Operation(summary = "Get list of all needs by their status", description = "Get all needs")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully fetched Needs list", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)),
            @ApiResponse(responseCode = "400", description = "Bad Input"),
            @ApiResponse(responseCode = "500", description = "Server Error")}
    )
    @GetMapping(value = "/",
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    Page<NeedEntityAndRequirement> getAllNeeds(
            @RequestParam(required = false, defaultValue = "0")  Integer page,
            @RequestParam(required = false, defaultValue = "10")  Integer size,
            @RequestParam(required = true) NeedStatus status,
            @Parameter() @RequestHeader Map<String, String> headers);

    @Operation(summary = "Get list of all needs by the ncoordinator userId", description = "Get all needs for user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully fetched Needs list", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)),
            @ApiResponse(responseCode = "400", description = "Bad Input"),
            @ApiResponse(responseCode = "500", description = "Server Error")}
    )
    @GetMapping(value = "/user/{userId}",
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    Page<Need> getAllNeedsForUser(
            @PathVariable @NotNull(message = "Field is missing") @NotEmpty(message = "Field cannot be empty") String userId,
            @RequestParam(required = false, defaultValue = "0")  Integer page,
            @RequestParam(required = false, defaultValue = "10")  Integer size,
            @RequestParam(required = true)  NeedStatus status,
            @Parameter() @RequestHeader Map<String, String> headers);


    @Operation(summary = "Get list of all needs by their need type Id", description = "Get all needs for need type")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully fetched Needs list", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)),
            @ApiResponse(responseCode = "400", description = "Bad Input"),
            @ApiResponse(responseCode = "500", description = "Server Error")}
    )
    @GetMapping(value = "/need-type/{needTypeId}",
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    Page<Need> getAllNeedsForNeedType(
            @PathVariable @NotNull(message = "Field is missing") @NotEmpty(message = "Field cannot be empty") String needTypeId,
            @RequestParam(required = false, defaultValue = "0")  Integer page,
            @RequestParam(required = false, defaultValue = "10")  Integer size,
            @RequestParam(required = true)  NeedStatus status,
            @Parameter() @RequestHeader Map<String, String> headers);


    @Operation(summary = "Create new need with status as New", description = "Add a need")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created a need", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)),
            @ApiResponse(responseCode = "400", description = "Bad Input"),
            @ApiResponse(responseCode = "500", description = "Server Error")}
    )
    @PostMapping(value = "/",
            produces = {MediaType.APPLICATION_JSON_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE}
    )
    Need createNeed(
            @RequestBody RaiseNeedRequest request,
            @Parameter() @RequestHeader Map<String, String> headers);


    @Operation(summary = "As a volunteer, I want to register , so that i can nominate the need", description = "Nominate a need")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully nominated a need", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)),
            @ApiResponse(responseCode = "400", description = "Bad Input"),
            @ApiResponse(responseCode = "500", description = "Server Error")}
    )
    @PostMapping(value = "/{needId}/nominate/{userId}",
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    Nomination nominateNeed(
            @PathVariable @NotNull(message = "Field is missing") @NotEmpty(message = "Field cannot be empty") String needId,
            @PathVariable @NotNull(message = "Field is missing") @NotEmpty(message = "Field cannot be empty") String userId,
            @Parameter() @RequestHeader Map<String, String> headers);


    @Operation(summary = "As a ncoordinator, i want to confirm/reject the volunteer, so that volunteer can be notified accordingly", description = "Update a need")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully nominated a need", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)),
            @ApiResponse(responseCode = "400", description = "Bad Input"),
            @ApiResponse(responseCode = "500", description = "Server Error")}
    )
    @PostMapping(value = "/{needId}/nominate/{userId}/confirm/{nominationId}",
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    Nomination updateNeed(
            @PathVariable @NotNull(message = "Field is missing") @NotEmpty(message = "Field cannot be empty") String userId,
            @PathVariable @NotNull(message = "Field is missing") @NotEmpty(message = "Field cannot be empty") String nominationId,
            @RequestParam(required = true) NominationStatus status,
            @Parameter() @RequestHeader Map<String, String> headers);


    @Operation(summary = "As a ncoordinator , i want to view the details of volunteers who had nominated the need, so that i can decide to confirm or reject", description = "Get all volunteers for nominated need")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully fetched Volunteer list", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)),
            @ApiResponse(responseCode = "400", description = "Bad Input"),
            @ApiResponse(responseCode = "500", description = "Server Error")}
    )
    @GetMapping(value = "/{needId}/nominate",
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    List<Nomination> getAllNominations(
            @PathVariable @NotNull(message = "Field is missing") @NotEmpty(message = "Field cannot be empty") String needId,
            @Parameter() @RequestHeader Map<String, String> headers);


    @Operation(summary = "As a ncoordinator , i want to view the details of volunteers who had nominated the need, so that i can decide to confirm or reject", description = "Get all volunteers for nominated need")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully fetched Volunteer list", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)),
            @ApiResponse(responseCode = "400", description = "Bad Input"),
            @ApiResponse(responseCode = "500", description = "Server Error")}
    )
    @GetMapping(value = "/{needId}/nominate/{status}",
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    List<Nomination> getAllNominationsByStatus(
            @PathVariable @NotNull(message = "Field is missing") @NotEmpty(message = "Field cannot be empty") String needId,
            @PathVariable @NotNull(message = "Field is missing") @NotEmpty(message = "Field cannot be empty") NominationStatus status,
            @Parameter() @RequestHeader Map<String, String> headers);

    @Operation(summary = "Get list of nominations by nominated userid", description = "Get all nominations")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully fetched the nominations list", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)),
            @ApiResponse(responseCode = "400", description = "Bad Input"),
            @ApiResponse(responseCode = "500", description = "Server Error")
    })
    @GetMapping(value = "/nomination/{nominatedUserId}",
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    List<Nomination> getAllNominationForUser(
            @PathVariable @NotNull(message = "Field is missing") @NotEmpty(message = "Field cannot be empty") String nominatedUserId,
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer size,
            @Parameter() @RequestHeader Map<String, String> headers);


}
