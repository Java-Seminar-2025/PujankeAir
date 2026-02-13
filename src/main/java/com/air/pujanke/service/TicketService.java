package com.air.pujanke.service;

import com.air.pujanke.exception.exceptiontype.InvalidArgumentException;
import com.air.pujanke.model.custom.AmenitiesIdentity;
import com.air.pujanke.model.custom.Seat;
import com.air.pujanke.model.dto.*;
import com.air.pujanke.model.dto.amenity.AmenityDto;
import com.air.pujanke.model.dto.ticket.TicketStaticDetailsDto;
import com.air.pujanke.model.entity.AmenitiesEntity;
import com.air.pujanke.model.entity.TicketEntity;
import com.air.pujanke.model.mapper.TicketMapper;
import com.air.pujanke.repository.FlightRepository;
import com.air.pujanke.repository.ServiceRepository;
import com.air.pujanke.repository.TicketRepository;
import com.air.pujanke.repository.UserRepository;
import com.air.pujanke.service.security.TicketSecurity;
import com.air.pujanke.service.utility.TicketHelper;
import com.air.pujanke.service.validator.TicketBookingValidator;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PatchMapping;

import java.math.BigDecimal;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TicketService {

    private final TicketRepository ticketRepository;
    private final FlightRepository flightRepository;
    private final FlightService flightService;
    private final UserRepository userRepository;
    private final TicketBookingValidator validator;
    private final TicketSecurity ticketSecurity;
    private final ObjectMapper objectMapper;
    private final TicketMapper ticketMapper;
    private final ServiceRepository serviceRepository;

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

        var seatMap = TicketHelper.constructSeatMap(rows, columns, takenSeats);
        var rng = new Random();

        while (remainingFreeSeats > 0) {
            int k = rng.nextInt(remainingFreeSeats);
            int seatIndex = TicketHelper.pickKthClearBit(seatMap, rows * columns, k);
            Seat chosen = TicketHelper.indexToSeat(seatIndex, columns);
            ticket.setSeat(chosen);

            try { return ticketRepository.save(ticket).getTicketId(); }

            catch (DataIntegrityViolationException ex) {
                seatMap.set(seatIndex);
                remainingFreeSeats--;
            }
        }

        throw new InvalidArgumentException("No free seats left.", "/search");
    }

    @PreAuthorize("@ticketSecurity.isTicketOwner(#ticketId, #username)")
    @Transactional(readOnly = true)
    public TicketStaticDetailsDto getTicketDetails(Integer ticketId, String username) {
        var ticket =  ticketRepository.findById(ticketId).orElseThrow(() -> new InvalidArgumentException("Ticket not found."));
        return ticketMapper.toStaticDetailsDto(ticket);
    }

    @PreAuthorize("@ticketSecurity.isTicketOwner(#ticketId, #username)")
    @Transactional
    public void addAmenity(AmenityDto amenityDto, Integer ticketId, String username) {
        var ticket = ticketRepository.findById(ticketId).orElseThrow(() -> new InvalidArgumentException("Ticket not found."));
        var amenity = new AmenitiesEntity();
        var amenities = ticket.getAmenities();
        amenities.forEach(am -> {
            if (am.getAmenitiesId().getServiceId().equals(amenityDto.serviceId()))
                throw new InvalidArgumentException("Amenity is already added.", "/tickets/" + ticketId);
        });
        amenity.setTicket(ticket);
        amenity.setService(serviceRepository.findById(amenityDto.serviceId())
                .orElseThrow(() -> new InvalidArgumentException("Service not found.")));
        amenity.setQuantity(amenityDto.quantity());
        amenity.setAmenitiesId(new AmenitiesIdentity(ticketId, amenityDto.serviceId()));
        amenities.add(amenity);
        ticketRepository.save(ticket);
    }

    @PreAuthorize("@ticketSecurity.isTicketOwner(#ticketId, #username)")
    @Transactional
    public void removeAmenity(Integer serviceId, Integer ticketId, String username) {

        TicketEntity ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new InvalidArgumentException("Ticket not found.", "/home"));

        AmenitiesEntity amenity = ticket.getAmenities().stream()
                .filter(a -> a.getAmenitiesId() != null
                        && a.getAmenitiesId().getServiceId().equals(serviceId))
                .findFirst()
                .orElseThrow(() -> new InvalidArgumentException("Amenity not found.", "/tickets/" + ticketId));
        ticket.getAmenities().remove(amenity);
        amenity.setTicket(null);
    }

    @PreAuthorize("@ticketSecurity.isTicketOwner(#ticketId, #username)")
    @Transactional
    public void updateAmenity(Integer serviceId, Integer ticketId, Integer quantity, String username) {
    }

}
