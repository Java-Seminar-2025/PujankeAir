package com.air.pujanke.service.validator;

import com.air.pujanke.exception.exceptiontype.InvalidArgumentException;
import com.air.pujanke.model.dto.AirportCreationDto;
import com.air.pujanke.repository.AirportRepository;
import com.air.pujanke.repository.CityRepository;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AdminAirportCrudValidator {

    private final AirportRepository airportRepository;
    private final CityRepository cityRepository;

    public void validateAirportCreation(@NonNull AirportCreationDto airportDto) {
        if (!cityRepository.existsById(airportDto.cityZipcode()))
            throw new InvalidArgumentException("Invalid city.");
        else if (airportRepository.existsByIcaoCode(airportDto.icaoCode()))
            throw new InvalidArgumentException("An airport with this ICAO code already exists.");
    }

    public void validateAirportUpdate(@NonNull AirportCreationDto airportDto) {
        if (!cityRepository.existsById(airportDto.cityZipcode()))
            throw new InvalidArgumentException("Invalid city.");
    }

    public void validateAirportDeletion(@NotBlank @Size(max = 4, min = 4) String icaoCode) {
        if (!airportRepository.existsByIcaoCode(icaoCode))
            throw new InvalidArgumentException("Airport does not exist.");
    }
}
