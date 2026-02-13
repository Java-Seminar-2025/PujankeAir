package com.air.pujanke.service;

import com.air.pujanke.exception.exceptiontype.InvalidArgumentException;
import com.air.pujanke.model.dto.airport.AirportReadDto;
import com.air.pujanke.model.dto.airport.AirportModificationDto;
import com.air.pujanke.model.entity.AirportEntity;
import com.air.pujanke.repository.AirportRepository;
import com.air.pujanke.repository.CityRepository;
import com.air.pujanke.service.validator.AdminAirportCrudValidator;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AirportService {
    private final AirportRepository airportRepository;
    private final AdminAirportCrudValidator airportCrudValidator;
    private final ObjectMapper objectMapper;
    private final CityRepository cityRepository;

    public List<AirportReadDto> getAllAirportsAdmin() {
        List<AirportReadDto> airportDtos = new ArrayList<>();
        airportRepository.findAll()
                .forEach(airport -> airportDtos.add(objectMapper.convertValue(airport, AirportReadDto.class)));
        return airportDtos;
    }

    public AirportModificationDto getAirport(String icaoCode) {
        var airport = airportRepository.findByIcaoCode(icaoCode)
                .orElseThrow(() -> new InvalidArgumentException("Airport not found."));
        return new AirportModificationDto(airport.getAirportName(), airport.getIcaoCode(), airport.getCity().getZipcode());
    }

    public void createAirport(AirportModificationDto airportDto) {
        airportCrudValidator.validateAirportCreation(airportDto);
        AirportEntity airport = objectMapper.convertValue(airportDto, AirportEntity.class);
        cityRepository.findById(airportDto.cityZipcode()).ifPresent(airport::setCity);
        airportRepository.save(airport);
    }


    @Transactional
    public void updateAirport(AirportModificationDto airportDto) {
        airportCrudValidator.validateAirportUpdate(airportDto);
        AirportEntity airport = airportRepository.findByIcaoCode(airportDto.icaoCode()).orElseThrow(() -> new InvalidArgumentException("Airport not found."));
        airport.setAirportName(airportDto.airportName());
        cityRepository.findById(airportDto.cityZipcode()).ifPresent(airport::setCity);
        airportRepository.save(airport);
    }

    @Transactional
    public void deleteAirport(String icaoCode) {
        airportCrudValidator.validateAirportDeletion(icaoCode);
        airportRepository.deleteByIcaoCode(icaoCode);
    }
}
