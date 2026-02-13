package com.air.pujanke.controller.pub;

import com.air.pujanke.model.dto.flight.FlightSearchFormDto;
import com.air.pujanke.service.AirportService;
import com.air.pujanke.service.FlightService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/search")
@RequiredArgsConstructor
public class FlightSearchController {
    private final AirportService airportService;
    private final FlightService flightService;

    public static boolean hasSearched(FlightSearchFormDto searchDto) {
        return !(searchDto.baseFareLesserThan() == null && (searchDto.destinationAirportIcao() == null ||
                searchDto.destinationAirportIcao().isBlank()) && (searchDto.originAirportIcao() == null ||
                searchDto.originAirportIcao().isBlank()) && searchDto.takeoffDate() == null);
    }

    @GetMapping
    public String search(Model model, Pageable pageable, @ModelAttribute("flightSearch") @Valid FlightSearchFormDto flightSearchFormDto) {
        model.addAttribute("airports", airportService.getAllAirportsAdmin());

        if (hasSearched(flightSearchFormDto)) {
            model.addAttribute("flights", flightService.getFilteredSearchResults(flightSearchFormDto, pageable));
        }
        return "search";
    }
}
