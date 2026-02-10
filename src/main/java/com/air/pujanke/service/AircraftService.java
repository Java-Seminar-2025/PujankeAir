package com.air.pujanke.service;

import com.air.pujanke.model.dto.AircraftDto;
import com.air.pujanke.repository.AircraftRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AircraftService {
    private final AircraftRepository aircraftRepository;
    private final ObjectMapper mapper;

    public List<AircraftDto> getAllAircraft() {
        List<AircraftDto> aircraftDtos = new ArrayList<>();
        aircraftRepository.findAll().forEach(aircraft -> aircraftDtos.add(mapper.convertValue(aircraft, AircraftDto.class)));
        return aircraftDtos;
    }
}
