package com.air.pujanke.model.mapper;

import com.air.pujanke.exception.exceptiontype.InvalidArgumentException;
import com.air.pujanke.model.dto.flight.FlightModificationDto;
import com.air.pujanke.model.dto.flight.FlightSearchResultDto;
import com.air.pujanke.model.entity.FlightEntity;
import com.air.pujanke.repository.AircraftRepository;
import com.air.pujanke.repository.AirportRepository;
import com.air.pujanke.repository.PilotRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FlightMapper {

    private final ObjectMapper objectMapper;
    private final AircraftRepository aircraftRepository;
    private final PilotRepository pilotRepository;
    private final AirportRepository airportRepository;

    public FlightModificationDto toFlightModificationDto(FlightEntity flight) {
        return new FlightModificationDto(flight.getFlightId(), flight.getAircraft() == null ? null : flight.getAircraft().getAircraftId(),
                flight.getEstimatedDurationMinutes(), flight.getTakeoffDate(), flight.getTakeoffTime(),
                flight.getTakeoffAirport() == null ? null : flight.getTakeoffAirport().getIcaoCode(),
                flight.getDestinationAirport() == null ? null : flight.getDestinationAirport().getIcaoCode(),
                flight.getPilot() == null ? null : flight.getPilot().getPin(), flight.getBaseFare());
    }

    public FlightEntity toFlightEntity(FlightModificationDto flightDto) {
        FlightEntity flightEntity = objectMapper.convertValue(flightDto, FlightEntity.class);
        flightEntity.setAircraft(aircraftRepository.findById(flightDto.aircraftId())
                .orElseThrow(() -> new InvalidArgumentException("The aircraft doesn't exist.")));

        flightEntity.setDestinationAirport(airportRepository.findByIcaoCode(flightDto.destinationAirportIcao())
                .orElseThrow(() -> new InvalidArgumentException("The destination airport doesn't exist.")));

        flightEntity.setTakeoffAirport(airportRepository.findByIcaoCode(flightDto.takeoffAirportIcao())
                .orElseThrow(() -> new InvalidArgumentException("The takeoff airport doesn't exist.")));

        flightEntity.setPilot(pilotRepository.findByPin(flightDto.pilotPin())
                .orElseThrow(() -> new InvalidArgumentException("The pilot doesn't exist.")));

        return flightEntity;
    }

    public FlightSearchResultDto toFlightSearchResultDto(FlightEntity entity) {

        var destinationAirportPresent = entity.getDestinationAirport() != null;
        var destinationAirportCityPresent = destinationAirportPresent && entity.getDestinationAirport().getCity() != null;
        var originAirportPresent = entity.getTakeoffAirport() != null;
        var originAirportCityPresent = originAirportPresent && entity.getTakeoffAirport().getCity() != null;

        var destinationIcao = destinationAirportPresent ?  entity.getDestinationAirport().getIcaoCode() : null;
        var destinationName = destinationAirportPresent ? entity.getDestinationAirport().getAirportName() : null;
        var destinationCity =  destinationAirportCityPresent ?  entity.getDestinationAirport().getCity().getCityName() : null;

        var originIcao = originAirportPresent ? entity.getTakeoffAirport().getIcaoCode() : null;
        var originName =  originAirportPresent ? entity.getTakeoffAirport().getAirportName() : null;
        var originCity =  originAirportCityPresent ? entity.getTakeoffAirport().getCity().getCityName() : null;

        return new FlightSearchResultDto(entity.getFlightId(), entity.getTakeoffDate(), entity.getTakeoffTime(),
                originName, originIcao, originCity, destinationName, destinationIcao, destinationCity, entity.getBaseFare());
    }
}
