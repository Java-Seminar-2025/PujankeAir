package com.air.pujanke.service.validator;

import com.air.pujanke.exception.exceptiontype.InvalidArgumentException;
import com.air.pujanke.model.dto.FlightModificationDto;
import com.air.pujanke.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;

@Component
@RequiredArgsConstructor
public class AdminFlightCrudValidator {
    private final FlightRepository flightRepository;
    private final TicketRepository ticketRepository;

    public void validateFlightModification(FlightModificationDto flightDto) {
        if (flightDto.takeoffDate().equals(LocalDate.now()) && flightDto.takeoffTime().isBefore(LocalTime.now())
        || flightDto.takeoffDate().isBefore(LocalDate.now()))
            throw new InvalidArgumentException("Cannot schedule a flight in the past.");
    }

    public void validateFlightDeletion(Integer flightId) {
        if (ticketRepository.existsByFlight(flightRepository.findById(flightId)
                .orElseThrow(() -> new  InvalidArgumentException("Flight doesn't exist."))))
            throw new InvalidArgumentException("Can't delete a flight that has tickets associated with it.", "/admin/flights");
    }
}
