package com.air.pujanke.controller.api;

import com.air.pujanke.model.dto.flight.FlightSeatConfigurationDto;
import com.air.pujanke.service.FlightService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/flights")
@RequiredArgsConstructor
public class FlightRestController {

    private final FlightService flightService;

    @GetMapping("/seat-config/{flightId}")
    public ResponseEntity<FlightSeatConfigurationDto> getSeatConfiguration(@PathVariable Integer flightId) {
        var flightSeatConfig = flightService.getFlightSeatConfiguration(flightId);
        return ResponseEntity.ok(flightSeatConfig);
    }

}
