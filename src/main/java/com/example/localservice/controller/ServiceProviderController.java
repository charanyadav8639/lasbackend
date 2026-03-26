package com.example.localservice.controller;

import java.util.stream.Collectors;
import java.util.List;

import com.example.localservice.dto.ServiceProviderRequestDTO;
import com.example.localservice.dto.ServiceProviderResponseDTO;
import com.example.localservice.model.ServiceProvider;
import com.example.localservice.repository.ServiceProviderRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.transaction.annotation.Transactional;

@RestController
@RequestMapping("/api/service-providers")
@Transactional(readOnly = true)
public class ServiceProviderController {

    @Autowired
    private ServiceProviderRepository providerRepository;

    @GetMapping
    public List<ServiceProviderResponseDTO> getAllProviders(@RequestParam(name = "location", required = false) String location) {
        return providerRepository.findAll().stream()
                .filter(p -> location == null || location.isEmpty() || 
                             (p.getLocation() != null && p.getLocation().toLowerCase().contains(location.toLowerCase())))
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/my")
    public List<ServiceProviderResponseDTO> getMyProviders(@RequestParam("ownerId") Long ownerId) {
        System.out.println("Fetching providers for ownerId: " + ownerId);
        List<ServiceProvider> providers = providerRepository.findAll();
        System.out.println("Total providers found: " + providers.size());
        return providers.stream()
                .filter(p -> {
                    boolean match = p.getOwnerId() != null && p.getOwnerId().equals(ownerId);
                    if (match) System.out.println("Match found: " + p.getName());
                    return match;
                })
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @PostMapping
    @Transactional
    public ResponseEntity<ServiceProviderResponseDTO> createProvider(@Valid @RequestBody ServiceProviderRequestDTO request) {
        ServiceProvider provider = new ServiceProvider();
        provider.setName(request.getName());
        provider.setServiceType(request.getServiceType());
        provider.setProviderType(request.getProviderType());
        provider.setLocation(request.getLocation());
        provider.setPhone(request.getPhone());
        provider.setOwnerId(request.getOwnerId());
        System.out.println("Creating provider for ownerId: " + request.getOwnerId());
        provider.setAvailability(request.getAvailability() != null ? request.getAvailability() : true);
        
        ServiceProvider saved = providerRepository.save(provider);
        return ResponseEntity.status(HttpStatus.CREATED).body(toDto(saved));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> deleteProvider(@PathVariable("id") Long id, @RequestParam(name = "userId", required = false) Long userId, @RequestParam(name = "isAdmin", required = false) Boolean isAdmin) {
        ServiceProvider provider = providerRepository.findById(id)
                .orElseThrow(() -> new org.springframework.web.server.ResponseStatusException(HttpStatus.NOT_FOUND, "Provider not found"));
        
        if (Boolean.TRUE.equals(isAdmin)) {
            providerRepository.delete(provider);
            return ResponseEntity.noContent().build();
        }

        if (userId == null || !provider.getOwnerId().equals(userId)) {
            throw new org.springframework.web.server.ResponseStatusException(HttpStatus.FORBIDDEN, "Not authorized to delete this provider");
        }

        providerRepository.delete(provider);
        return ResponseEntity.noContent().build();
    }

    private ServiceProviderResponseDTO toDto(ServiceProvider p) {
        List<com.example.localservice.dto.ServiceResponseDTO> servicesDto = p.getServices() != null ? 
            p.getServices().stream().map(this::toServiceDto).collect(Collectors.toList()) : null;
        List<com.example.localservice.dto.ServiceWorkerResponseDTO> workersDto = p.getWorkers() != null ? 
            p.getWorkers().stream().map(this::toWorkerDto).collect(Collectors.toList()) : null;

        return new ServiceProviderResponseDTO(
            p.getId(),
            p.getName(),
            p.getServiceType(),
            p.getLocation(),
            p.getPhone(),
            p.getAvailability(),
            p.getOwnerId(),
            p.getProviderType(),
            servicesDto,
            workersDto
        );
    }

    private com.example.localservice.dto.ServiceResponseDTO toServiceDto(com.example.localservice.model.Service s) {
        return new com.example.localservice.dto.ServiceResponseDTO(
            s.getId(),
            s.getServiceName(),
            s.getDescription(),
            s.getCategory(),
            s.getPrice(),
            s.getServiceProvider() != null ? s.getServiceProvider().getId() : null
        );
    }

    private com.example.localservice.dto.ServiceWorkerResponseDTO toWorkerDto(com.example.localservice.model.ServiceWorker w) {
        return new com.example.localservice.dto.ServiceWorkerResponseDTO(
            w.getId(),
            w.getName(),
            w.getPhone(),
            w.getSpecialization(),
            w.isAvailable(),
            w.getCompany() != null ? w.getCompany().getId() : null
        );
    }
}
