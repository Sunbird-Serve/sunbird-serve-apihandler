package com.evidyaloka.vneed.api;

import com.evidyaloka.vneed.models.VneedResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@Tag(name = "Vneed APIs")
@RequestMapping("/")
public interface VneedApi {
    @Operation(summary = "Discover need module", description = "Discover different types of need modules")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful response", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)),
            @ApiResponse(responseCode = "400", description = "Bad Input"),
            @ApiResponse(responseCode = "500", description = "Server Error")}
    )
    @GetMapping(value = "/{needModule}",
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    ResponseEntity<VneedResponse> read(
            @PathVariable @NotNull(message = "Field is missing") @NotEmpty(message = "Field cannot be empty") String needModule,
            @Parameter() @RequestHeader Map<String, String> headers);

}