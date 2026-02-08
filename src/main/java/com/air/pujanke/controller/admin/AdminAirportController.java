package com.air.pujanke.controller.admin;

import com.air.pujanke.model.dto.AirportCreationDto;
import com.air.pujanke.service.AirportService;
import com.air.pujanke.service.CityService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/airport")
public class AdminAirportController {

    private final CityService cityService;
    private final AirportService airportService;

    @GetMapping
    public String getAirportForm(Model model) {

        model.addAttribute("airports", airportService.getAllAirportsAdmin());
        model.addAttribute("airportForm", new AirportCreationDto(null, null, null));
        model.addAttribute("cities",  cityService.getAllCities());
        return "airport";
    }

    @PostMapping
    public String addAirport(@ModelAttribute("airportForm") @Valid AirportCreationDto airportForm, RedirectAttributes ra) {
        airportService.createAirport(airportForm);
        ra.addFlashAttribute("success", "Airport has been successfully created.");
        return "redirect:/admin/airport";
    }

    @DeleteMapping("/{icaoCode}")
    public String deleteAirport(@PathVariable String icaoCode, RedirectAttributes ra) {
        airportService.deleteAirport(icaoCode);
        ra.addFlashAttribute("success", "Airport successsfully deleted.");
        return "redirect:/admin/airport";
    }

    @GetMapping("/{icaoCode}")
    public String getAirportUpdateForm(@PathVariable String icaoCode, Model model) {
        model.addAttribute("airportForm", airportService.getAirport(icaoCode));
        model.addAttribute("cities", cityService.getAllCities());
        return "airport_edit";
    }

    @PatchMapping
    public String updateAirport(@ModelAttribute("airportForm") @Valid AirportCreationDto airportDto, RedirectAttributes ra) {

        airportService.updateAirport(airportDto);
        ra.addFlashAttribute("success", "Airport successsfully updated.");
        return "redirect:/admin/airport";
    }
}
