package com.air.pujanke.service;

import com.air.pujanke.exception.exceptiontype.InvalidArgumentException;
import com.air.pujanke.model.dto.FlightModificationDto;
import com.air.pujanke.model.dto.FlightReadDto;
import com.air.pujanke.model.entity.FlightEntity;
import com.air.pujanke.model.mapper.FlightMapper;
import com.air.pujanke.repository.AircraftRepository;
import com.air.pujanke.repository.AirportRepository;
import com.air.pujanke.repository.FlightRepository;
import com.air.pujanke.repository.PilotRepository;
import com.air.pujanke.service.validator.AdminFlightCrudValidator;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FlightService {
    private final FlightRepository flightRepository;
    private final AdminFlightCrudValidator validator;
    private final ObjectMapper objectMapper;
    private final FlightMapper customMapper;

    public List<FlightReadDto> getAllFlights(boolean includeOutdated) {
        List<FlightReadDto> flights = new ArrayList<>();
        List<FlightEntity> flightEntities;
        if (!includeOutdated)
            flightEntities = flightRepository.findAllByTakeoffDateBeforeAndTakeoffTimeBefore(LocalDate.now(), LocalTime.now());
        else
            flightEntities = flightRepository.findAll();

        flightEntities.forEach(flight -> flights.add(objectMapper.convertValue(flight, FlightReadDto.class)));
        return flights;
    }

    public FlightModificationDto getFlight(@PathVariable Integer flightId) {
        var flight =  flightRepository.findById(flightId)
                .orElseThrow(() -> new InvalidArgumentException("Flight not found."));
        return customMapper.toFlightModificationDto(flight);
    }

    public void scheduleFlight(FlightModificationDto flightDto) {
        validator.validateFlightModification(flightDto);
        flightRepository.save(customMapper.toFlightEntity(flightDto));
    }

    @Transactional
    public void updateFlight(@PathVariable Integer flightId, FlightModificationDto flightModificationDto) {
        validator.validateFlightModification(flightModificationDto);
        flightRepository.save(customMapper.toFlightEntity(flightModificationDto));
    }

    @Transactional
    public void deleteFlight(Integer flightId) {
        validator.validateFlightDeletion(flightId);
        flightRepository.deleteById(flightId);
    }
}
