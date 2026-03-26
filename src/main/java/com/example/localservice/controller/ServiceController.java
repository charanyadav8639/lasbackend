package com.example.localservice.controller;

import com.example.localservice.model.ServiceType;

import com.example.localservice.dto.ServiceRequestDTO;
import com.example.localservice.dto.ServiceResponseDTO;
import com.example.localservice.model.Service;
import com.example.localservice.model.ServiceProvider;
import com.example.localservice.repository.ServiceProviderRepository;
import com.example.localservice.repository.ServiceRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.transaction.annotation.Transactional;

@RestController
@RequestMapping("/api/services")
@Transactional(readOnly = true)
public class ServiceController {

    @Autowired
    private ServiceRepository serviceRepository;

    @Autowired
    private ServiceProviderRepository providerRepository;

    @GetMapping
    public List<ServiceResponseDTO> getServices(@RequestParam(name = "providerId", required = false) Long providerId) {
        return serviceRepository.findAll().stream()
                .filter(s -> providerId == null || (s.getServiceProvider() != null && s.getServiceProvider().getId().equals(providerId)))
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> deleteService(@PathVariable("id") Long id, @RequestParam(name = "userId", required = false) Long userId, @RequestParam(name = "isAdmin", required = false) Boolean isAdmin) {
        Service service = serviceRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Service not found"));
        
        if (Boolean.TRUE.equals(isAdmin)) {
            serviceRepository.delete(service);
            return ResponseEntity.noContent().build();
        }

        if (userId == null || service.getServiceProvider() == null || !service.getServiceProvider().getOwnerId().equals(userId)) {
             throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Not authorized to delete this service");
        }
        
        serviceRepository.delete(service);
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    @Transactional
    public ResponseEntity<ServiceResponseDTO> createService(@Valid @RequestBody ServiceRequestDTO request) {
        ServiceProvider provider = providerRepository.findById(request.getProviderId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Service provider not found"));

        Service service = new Service();
        service.setServiceName(request.getServiceName());
        service.setDescription(request.getDescription());
        service.setCategory(request.getCategory());
        service.setPrice(request.getPrice());
        service.setServiceProvider(provider);

        Service saved = serviceRepository.save(service);
        return ResponseEntity.status(HttpStatus.CREATED).body(toDto(saved));
    }

    private ServiceResponseDTO toDto(Service service){
        return new ServiceResponseDTO(
                service.getId(),
                service.getServiceName(),
                service.getDescription(),
                service.getCategory(),
                service.getPrice(),
                service.getServiceProvider() != null ? service.getServiceProvider().getId() : null
        );
    }
}
