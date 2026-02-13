package com.air.pujanke.model.dto.ticket;

import com.air.pujanke.model.custom.Seat;
import com.air.pujanke.model.dto.amenity.AmenityDto;
import com.air.pujanke.model.dto.airport.AirportReadDto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public record TicketStaticDetailsDto(AirportReadDto originAirport,
                                     AirportReadDto destinationAirport,
                                     LocalDate takeoffDate,
                                     LocalTime takeoffTime,
                                     Seat assignedSeat,
                                     BigDecimal ticketPrice,
                                     List<AmenityDto> amenities,
                                     BigDecimal amenitiesPrice) {
}
