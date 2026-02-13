package com.air.pujanke.model.mapper;

import com.air.pujanke.model.dto.airport.AirportReadDto;
import com.air.pujanke.model.dto.amenity.AmenityDto;
import com.air.pujanke.model.dto.CityDto;
import com.air.pujanke.model.dto.ticket.TicketStaticDetailsDto;
import com.air.pujanke.model.entity.TicketEntity;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
@RequiredArgsConstructor
public class TicketMapper {

    private final ObjectMapper objectMapper;

    public TicketStaticDetailsDto toStaticDetailsDto(TicketEntity ticket) {
        var amenities =  ticket.getAmenities();
        List<AmenityDto> amenityDtos = amenities.stream()
                .map(amenity -> new AmenityDto(
                        amenity.getAmenitiesId().getServiceId(), amenity.getService().getServiceName(),
                        amenity.getService().getServiceFee(), amenity.getQuantity())
                ).toList();


        var flight = ticket.getFlight();
        var destination = flight.getDestinationAirport();
        var origin = flight.getTakeoffAirport();


        int totalQuantity = amenityDtos.stream()
                .mapToInt(AmenityDto::quantity)
                .sum();

        BigDecimal amenitiesPrice = amenityDtos.stream()
                .map(a -> a.serviceFee().multiply(BigDecimal.valueOf(a.quantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return new TicketStaticDetailsDto(
                new AirportReadDto(
                        origin.getAirportName(), origin.getIcaoCode(),
                        objectMapper.convertValue(origin.getCity(), CityDto.class)
                ),
                new AirportReadDto(
                        destination.getAirportName(), destination.getIcaoCode(),
                        objectMapper.convertValue(destination.getCity(), CityDto.class)
                ),
                flight.getTakeoffDate(), flight.getTakeoffTime(), ticket.getSeat(),
                ticket.getTicketPrice(), amenityDtos, amenitiesPrice
        );
    }
}
