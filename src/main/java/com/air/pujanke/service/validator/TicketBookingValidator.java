package com.air.pujanke.service.validator;

import com.air.pujanke.exception.exceptiontype.InvalidArgumentException;
import com.air.pujanke.model.custom.Seat;
import com.air.pujanke.model.entity.AircraftEntity;
import com.air.pujanke.model.entity.FlightEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class TicketBookingValidator {

    public void validateSelectedReservation(AircraftEntity aircraft, Seat seat) {
        if (aircraft == null)
            throw new InvalidArgumentException("Cannot book a ticket on a flight with no aircraft.");
        else if (seat.columnCharToIndex() > aircraft.getSeatColumnCount() || seat.getSeatRow() > aircraft.getSeatRowCount())
            throw new InvalidArgumentException("Invalid seat pick");
    }

    public void validateRandomReservation(FlightEntity flight, List<Seat> takenSeats) {
        if (flight.getAircraft() == null)
            throw new InvalidArgumentException("Cannot book a ticket on a flight with no aircraft.");
        else if (flight.getAircraft().getSeatColumnCount() * flight.getAircraft().getSeatRowCount() <= takenSeats.size())
            throw new InvalidArgumentException("No free seats left.", "/search");
    }
}
