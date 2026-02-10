package com.air.pujanke.service;

import com.air.pujanke.model.dto.PilotDto;
import com.air.pujanke.repository.PilotRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PilotService {
    private final PilotRepository pilotRepository;
    private final ObjectMapper objectMapper;

    public List<PilotDto> getAllPilots() {
        List<PilotDto> pilots = new ArrayList<>();
        pilotRepository.findAll().forEach(pilot -> pilots.add(objectMapper.convertValue(pilot, PilotDto.class)));
        return pilots;
    }
}
