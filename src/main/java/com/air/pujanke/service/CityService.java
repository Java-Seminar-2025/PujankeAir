package com.air.pujanke.service;

import com.air.pujanke.model.dto.CityDto;
import com.air.pujanke.repository.CityRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CityService {

    private final ObjectMapper mapper;
    private final CityRepository cityRepository;

    public List<CityDto> getAllCities() {
        List<CityDto> cityDtos = new ArrayList<>();
        cityRepository.findAll().forEach( city -> cityDtos.add(mapper.convertValue(city, CityDto.class)));
        return cityDtos;
    }
}
