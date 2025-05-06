package com.p1g14.pomodoro_timer_api.timer;

import com.p1g14.pomodoro_timer_api.timer.dto.TimerCreateRequest;
import com.p1g14.pomodoro_timer_api.timer.dto.TimerDetailsResponse;
import com.p1g14.pomodoro_timer_api.timer.dto.TimerUpdateRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/timers")
@RequiredArgsConstructor
public class TimerController {

    private final TimerService timerService;

    @Operation(summary = "Get a timer by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Request succeeded. The response contains the requested data."),
            @ApiResponse(responseCode = "400", description = "The request is invalid or malformed. Check input parameters or data format."),
            @ApiResponse(responseCode = "401", description = "Authentication failed or missing. A valid token or credentials are required."),
            @ApiResponse(responseCode = "403", description = "You are authenticated, but not authorized to perform this action."),
            @ApiResponse(responseCode = "404", description = "The requested resource could not be found. It may not exist or the path is incorrect."),
            @ApiResponse(responseCode = "500", description = "An unexpected error occurred on the server. Please try again later.")
    })
    @GetMapping("/{id}")
    public ResponseEntity<TimerDetailsResponse> getTimerById(@PathVariable Long id) {
        return ResponseEntity.ok(timerService.getTimerById(id));
    }

    @Operation(summary = "Update timer of ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Request succeeded. The response contains the requested data."),
            @ApiResponse(responseCode = "400", description = "The request is invalid or malformed. Check input parameters or data format."),
            @ApiResponse(responseCode = "401", description = "Authentication failed or missing. A valid token or credentials are required."),
            @ApiResponse(responseCode = "403", description = "You are authenticated, but not authorized to perform this action."),
            @ApiResponse(responseCode = "404", description = "The requested resource could not be found. It may not exist or the path is incorrect."),
            @ApiResponse(responseCode = "500", description = "An unexpected error occurred on the server. Please try again later.")
    })
    @PutMapping("/{id}")
    public ResponseEntity<TimerDetailsResponse> updateTimer(@PathVariable Long id, @RequestBody @Valid TimerUpdateRequest dto) {
        return ResponseEntity.ok(timerService.updateTimer(id, dto));
    }

    //Spring will automatically trigger bean‐validation when we annotate the @RequestBody parameter with @Valid
    @Operation(summary = "Create a new timer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Resource successfully created. A new resource has been added as a result of the request."),
            @ApiResponse(responseCode = "400", description = "The request is invalid or malformed. Check input parameters or data format."),
            @ApiResponse(responseCode = "401", description = "Authentication failed or missing. A valid token or credentials are required."),
            @ApiResponse(responseCode = "500", description = "An unexpected error occurred on the server. Please try again later.")
    })
    @PostMapping
    public ResponseEntity<TimerDetailsResponse> createTimer(@RequestBody @Valid TimerCreateRequest dto) {
        TimerDetailsResponse createdTimer = timerService.createTimer(dto);
        URI url = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(createdTimer.getId()).toUri();
        return ResponseEntity.created(url).build();
    }


    @Operation(summary = "Delete timer of ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Request succeeded, but there's no content to return in the response body."),
            @ApiResponse(responseCode = "400", description = "The request is invalid or malformed. Check input parameters or data format."),
            @ApiResponse(responseCode = "401", description = "Authentication failed or missing. A valid token or credentials are required."),
            @ApiResponse(responseCode = "403", description = "You are authenticated, but not authorized to perform this action."),
            @ApiResponse(responseCode = "404", description = "The requested resource could not be found. It may not exist or the path is incorrect."),
            @ApiResponse(responseCode = "500", description = "An unexpected error occurred on the server. Please try again later.")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTimer(@PathVariable Long id) {
        timerService.deleteTimer(id);
        return ResponseEntity.noContent().build();
    }
}
