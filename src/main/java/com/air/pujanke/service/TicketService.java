package com.air.pujanke.service;

import com.air.pujanke.exception.exceptiontype.InvalidArgumentException;
import com.air.pujanke.model.custom.Seat;
import com.air.pujanke.model.dto.SeatReservationDto;
import com.air.pujanke.model.entity.TicketEntity;
import com.air.pujanke.repository.FlightRepository;
import com.air.pujanke.repository.TicketRepository;
import com.air.pujanke.repository.UserRepository;
import com.air.pujanke.service.validator.TicketBookingValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.BitSet;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class TicketService {

    private final TicketRepository ticketRepository;
    private final FlightRepository flightRepository;
    private final FlightService flightService;
    private final UserRepository userRepository;
    private final TicketBookingValidator validator;

    public int reserveSelectedSeat(Integer flightId, SeatReservationDto seatDto, String username) {
        var flight = flightRepository.findById(flightId).orElseThrow(() -> new InvalidArgumentException("Flight not found."));
        var user = userRepository.findByUsername(username).orElseThrow(() -> new InvalidArgumentException("User not found."));
        var seat = Seat.fromString(seatDto.seatId());
        validator.validateSelectedReservation(flight.getAircraft(), seat);
        TicketEntity ticket = new TicketEntity();
        ticket.setFlight(flight);
        ticket.setSeat(seat);
        ticket.setUser(user);
        ticket.setTicketPrice(flight.getBaseFare().multiply(BigDecimal.valueOf(1.25)));
        try {
            var savedTicket = ticketRepository.save(ticket);
            return savedTicket.getTicketId();
        }
        catch (DataIntegrityViolationException dex) {
            throw new InvalidArgumentException("The seat is already occupied.", "/flights/" + flightId);
        }
    }

    private static int seatToIndex(Seat seat, int cols) {
        return (seat.getSeatRow() - 1) * cols + seat.columnCharToIndex();
    }

    private static Seat indexToSeat(int idx, int cols) {
        int row = (idx / cols) + 1;
        int colIndex = idx % cols;
        char col = Seat.indexToColumnChar(colIndex);
        return new Seat(row, col);
    }

    private static BitSet constructSeatMap(int rows, int cols, List<Seat> takenSeats) {
        var seatMap = new BitSet(rows * cols);
        takenSeats.stream().mapToInt((seat) -> seatToIndex(seat, cols)).forEach(seatMap::set);
        return seatMap;
    }

    private static int pickKthClearBit(BitSet taken, int totalSeats, int k) {
        int index = taken.nextClearBit(0);
        while (k > 0 && index >= 0 && index < totalSeats) {
            index = taken.nextClearBit(index + 1);
            k--;
        }
        return index;
    }

    public int reserveRandomSeat(Integer flightId, String username) {
        var flight = flightRepository.findById(flightId).orElseThrow(() -> new InvalidArgumentException("Flight not found."));
        var user = userRepository.findByUsername(username).orElseThrow(() -> new InvalidArgumentException("User not found."));
        var aircraft = flight.getAircraft();
        var takenSeats = flightService.getFlightSeatConfiguration(flightId).takenSeats();
        validator.validateRandomReservation(flight, takenSeats);

        int rows = aircraft.getSeatRowCount();
        int columns = aircraft.getSeatColumnCount();
        int remainingFreeSeats = rows * columns - takenSeats.size();

        TicketEntity ticket = new TicketEntity();
        ticket.setFlight(flight);
        ticket.setUser(user);
        ticket.setTicketPrice(flight.getBaseFare());

        var seatMap = constructSeatMap(rows, columns, takenSeats);
        var rng = new Random();

        while (remainingFreeSeats > 0) {
            int k = rng.nextInt(remainingFreeSeats);
            int seatIndex = pickKthClearBit(seatMap, rows * columns, k);
            Seat chosen = indexToSeat(seatIndex, columns);
            ticket.setSeat(chosen);

            try { return ticketRepository.save(ticket).getTicketId(); }

            catch (DataIntegrityViolationException ex) {
                seatMap.set(seatIndex);
                remainingFreeSeats--;
            }
        }

        throw new InvalidArgumentException("No free seats left.", "/search");
    }
}
