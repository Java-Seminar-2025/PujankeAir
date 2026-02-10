package com.air.pujanke.controller.admin;


import com.air.pujanke.model.dto.FlightModificationDto;
import com.air.pujanke.service.AircraftService;
import com.air.pujanke.service.AirportService;
import com.air.pujanke.service.FlightService;
import com.air.pujanke.service.PilotService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/flights")
public class AdminFlightController {

    private final FlightService flightService;
    private final PilotService pilotService;
    private final AirportService airportService;
    private final AircraftService aircraftService;

    @GetMapping
    public String getFlights(Model model) {
        model.addAttribute("flightForm",
                new FlightModificationDto(null, null, null,null,
                        null,null,null,null, null));

        model.addAttribute("flights", flightService.getAllFlights(true));
        model.addAttribute("pilots", pilotService.getAllPilots());
        model.addAttribute("airports", airportService.getAllAirportsAdmin());
        model.addAttribute("aircrafts", aircraftService.getAllAircraft());
        return "flight";
    }

    @PostMapping
    public String scheduleFlight(@ModelAttribute("flightForm") @Valid FlightModificationDto flightDto,
                               RedirectAttributes ra) {
        flightService.scheduleFlight(flightDto);
        ra.addFlashAttribute("success", "Flight scheduled successfully.");
        return "redirect:/admin/flights";
    }

    @GetMapping("/{flightId}")
    public String getFlightEditPage(@PathVariable Integer flightId, Model model) {
        model.addAttribute("flightForm", flightService.getFlight(flightId));
        model.addAttribute("pilots", pilotService.getAllPilots());
        model.addAttribute("airports", airportService.getAllAirportsAdmin());
        model.addAttribute("aircrafts", aircraftService.getAllAircraft());
        return "flight_edit";
    }


    @PatchMapping("/{flightId}")
    public String updateFlightDetails(@PathVariable Integer flightId,
                                      @ModelAttribute("flightForm") @Valid FlightModificationDto flightFormDto,
                                      RedirectAttributes ra) {
        flightService.updateFlight(flightId, flightFormDto);
        ra.addFlashAttribute("success", "Flight updated successfully.");
        return "redirect:/admin/flights";
    }

    @DeleteMapping("/{flightId}")
    public String deleteFlight(@PathVariable Integer flightId, RedirectAttributes ra) {
        flightService.deleteFlight(flightId);
        ra.addFlashAttribute("success", "Flight deleted successfully.");
        return "redirect:/admin/flights";
    }
}
