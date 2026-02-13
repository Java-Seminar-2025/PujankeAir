package com.air.pujanke.controller.user;

import com.air.pujanke.model.dto.amenity.AmenityDto;
import com.air.pujanke.service.TicketService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@Controller
@RequestMapping("/tickets/{ticketId}/amenity")
public class TicketAmenityController {

    private final TicketService ticketService;

    public TicketAmenityController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @PostMapping
    public String addAmenity(@PathVariable Integer ticketId, @ModelAttribute("amenityForm") @Valid AmenityDto amenityDto,
                             Principal principal, RedirectAttributes ra) {
        ticketService.addAmenity(amenityDto, ticketId, principal.getName());
        ra.addFlashAttribute("success", "Successfully added amenity.");
        return "redirect:/tickets/" +  ticketId;
    }

    @DeleteMapping("/{serviceId}")
    public String removeAmenity(@PathVariable Integer ticketId, @PathVariable Integer serviceId,  Principal principal,
                                RedirectAttributes ra) {
        ticketService.removeAmenity(serviceId, ticketId, principal.getName());
        ra.addFlashAttribute("success", "Successfully removed amenity.");
        return "redirect:/tickets/" +  ticketId;
    }

    public String updateQuantity(@PathVariable Integer ticketId, Integer newQuantity, Principal principal, RedirectAttributes ra) {
        return "home";
    }
}
