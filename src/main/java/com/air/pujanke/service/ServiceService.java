package com.air.pujanke.service;

import com.air.pujanke.model.dto.amenity.ServiceDto;
import com.air.pujanke.repository.ServiceRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ServiceService {
    private final ServiceRepository serviceRepository;
    private final ObjectMapper objectMapper;

    public List<ServiceDto> getAllServices() {
        return serviceRepository.findAll().stream()
                .map((service) -> objectMapper.convertValue(service, ServiceDto.class))
                .toList();
    }
}
