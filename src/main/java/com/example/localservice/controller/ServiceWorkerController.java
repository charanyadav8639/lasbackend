package com.example.localservice.controller;

import com.example.localservice.dto.ServiceWorkerRequestDTO;
import com.example.localservice.dto.ServiceWorkerResponseDTO;
import com.example.localservice.model.ServiceProvider;
import com.example.localservice.model.ServiceWorker;
import com.example.localservice.repository.ServiceProviderRepository;
import com.example.localservice.repository.ServiceWorkerRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/service-workers")
public class ServiceWorkerController {

    @Autowired
    private ServiceWorkerRepository workerRepository;

    @Autowired
    private ServiceProviderRepository providerRepository;

    @GetMapping
    public List<ServiceWorkerResponseDTO> getWorkersByCompany(@RequestParam("companyId") Long companyId) {
        return workerRepository.findByCompanyId(companyId)
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @PostMapping
    public ResponseEntity<ServiceWorkerResponseDTO> createWorker(@Valid @RequestBody ServiceWorkerRequestDTO request) {
        ServiceProvider company = providerRepository.findById(request.getCompanyId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Company not found"));

        ServiceWorker worker = new ServiceWorker();
        worker.setName(request.getName());
        worker.setPhone(request.getPhone());
        worker.setSpecialization(request.getSpecialization());
        worker.setCompany(company);

        ServiceWorker saved = workerRepository.save(worker);
        return ResponseEntity.status(HttpStatus.CREATED).body(toDto(saved));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWorker(@PathVariable("id") Long id, @RequestParam(name = "userId", required = false) Long userId, @RequestParam(name = "isAdmin", required = false) Boolean isAdmin) {
        ServiceWorker worker = workerRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Worker not found"));
        
        if (Boolean.TRUE.equals(isAdmin)) {
            workerRepository.delete(worker);
            return ResponseEntity.noContent().build();
        }

        if (userId == null || worker.getCompany() == null || !worker.getCompany().getOwnerId().equals(userId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Not authorized to delete this worker");
        }
        
        workerRepository.delete(worker);
        return ResponseEntity.noContent().build();
    }

    private ServiceWorkerResponseDTO toDto(ServiceWorker worker) {
        return new ServiceWorkerResponseDTO(
                worker.getId(),
                worker.getName(),
                worker.getPhone(),
                worker.getSpecialization(),
                worker.isAvailable(),
                worker.getCompany().getId()
        );
    }
}
