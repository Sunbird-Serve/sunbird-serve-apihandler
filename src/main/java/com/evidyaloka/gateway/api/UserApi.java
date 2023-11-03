package com.evidyaloka.gateway.api;

import com.evidyaloka.gateway.models.request.UserRequest;
import com.evidyaloka.gateway.models.response.RcUserResponse;
import com.evidyaloka.gateway.models.response.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequestMapping("/user")
public interface UserApi {

    @Operation(summary = "Get a particular user by its primary key", description = "Get User by Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully fetched User", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)),
            @ApiResponse(responseCode = "400", description = "Bad Input"),
            @ApiResponse(responseCode = "500", description = "Server Error")}
    )
    @GetMapping(value = "/{userId}",
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    ResponseEntity<User> getUserById(
            @PathVariable @NotNull(message = "Field is missing") @NotEmpty(message = "Field cannot be empty") String userId,
            @Parameter() @RequestHeader Map<String, String> headers);


    @Operation(summary = "Get all users by optional email", description = "Get Users by optional email")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully fetched User", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)),
            @ApiResponse(responseCode = "400", description = "Bad Input"),
            @ApiResponse(responseCode = "500", description = "Server Error")}
    )
    @GetMapping(value = "/email",
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    ResponseEntity<User> getUserByEmail(
            @RequestParam(required = true)  String email,
            @Parameter() @RequestHeader Map<String, String> headers);

    @Operation(summary = "Get all users", description = "Get all Users")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully fetched Users", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)),
            @ApiResponse(responseCode = "400", description = "Bad Input"),
            @ApiResponse(responseCode = "500", description = "Server Error")}
    )
    @GetMapping(value = "/list",
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    ResponseEntity<List<User>> getAllUsers(
            @Parameter() @RequestHeader Map<String, String> headers);


    @Operation(summary = "Create new user", description = "Create a user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created a user", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)),
            @ApiResponse(responseCode = "400", description = "Bad Input"),
            @ApiResponse(responseCode = "500", description = "Server Error")}
    )
    @PostMapping(value = "/",
            produces = {MediaType.APPLICATION_JSON_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE}
    )
    ResponseEntity<RcUserResponse> createUser(
            @RequestBody UserRequest userRequest,
            @Parameter() @RequestHeader Map<String, String> headers);



    @Operation(summary = "Update user", description = "Update a user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully updated a user", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)),
            @ApiResponse(responseCode = "400", description = "Bad Input"),
            @ApiResponse(responseCode = "500", description = "Server Error")}
    )
    @PostMapping(value = "/{userId}",
            produces = {MediaType.APPLICATION_JSON_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE}
    )
    ResponseEntity<RcUserResponse> updateUser(
            @PathVariable @NotNull(message = "Field is missing") @NotEmpty(message = "Field cannot be empty") String userId,
            @RequestBody UserRequest userRequest,
            @Parameter() @RequestHeader Map<String, String> headers);


}
