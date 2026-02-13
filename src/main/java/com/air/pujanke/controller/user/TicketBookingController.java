package com.air.pujanke.controller.user;

import com.air.pujanke.model.dto.SeatReservationDto;
import com.air.pujanke.model.dto.amenity.AmenityDto;
import com.air.pujanke.model.dto.ticket.TicketFinalizationDto;
import com.air.pujanke.service.FlightService;
import com.air.pujanke.service.ServiceService;
import com.air.pujanke.service.TicketService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
@RequestMapping
public class TicketBookingController {

    private final FlightService flightService;
    private final TicketService ticketService;
    private final ServiceService serviceService;

    @GetMapping("/flights/{flightId}")
    public String getSeatReservationPage(@PathVariable Integer flightId, Model model) {
        model.addAttribute("flightId", flightId);
        model.addAttribute("seatConfig", flightService.getFlightSeatConfiguration(flightId));
        return "seat_reservation";
    }

    @PostMapping("/flights/{flightId}/ticket")
    public String reservePickedSeat(@PathVariable Integer flightId, @ModelAttribute @Valid SeatReservationDto seatDto,
                              Principal principal, RedirectAttributes re) {
        var ticketId = ticketService.reserveSelectedSeat(flightId, seatDto, principal.getName());
        re.addFlashAttribute("success", "Successfully reserved seat " + seatDto.seatId() + ".");
        return "redirect:/tickets/" +  ticketId;
    }

    @PostMapping("/flights/{flightId}/ticket/random")
    public String reserveRandomSeat(@PathVariable Integer flightId, Principal principal, RedirectAttributes re) {
        var ticketId = ticketService.reserveRandomSeat(flightId, principal.getName());
        re.addFlashAttribute("success", "Successfully reserved seat.");
        return "redirect:/tickets/" +  ticketId;
    }

    @GetMapping("/tickets/{ticketId}")
    public String getTicketFinalizationPage(@PathVariable Integer ticketId, Model model, Principal principal) {
        model.addAttribute("finalizationForm", new TicketFinalizationDto(null, null));
        model.addAttribute("ticketId", ticketId);
        model.addAttribute("ticketDetails", ticketService.getTicketDetails(ticketId, principal.getName()));
        model.addAttribute("services", serviceService.getAllServices());
        model.addAttribute("amenityForm", new AmenityDto(null, null, null, null));
        return "ticket_finalization";
    }

    @PatchMapping("/tickets/{ticketId}")
    public String finalizeTicket(@PathVariable Integer ticketId,
                                 @ModelAttribute("finalizationForm") @Valid TicketFinalizationDto ticketFinalizeDto,
                                 Principal principal,
                                 RedirectAttributes ra) {
        ticketService.finalizeTicket(ticketId, principal.getName(), ticketFinalizeDto);
        ra.addFlashAttribute("success", "Successfully finalized ticket.");
        return "redirect:/home";
    }

}