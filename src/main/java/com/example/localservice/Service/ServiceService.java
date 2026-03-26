package com.example.localservice.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.localservice.model.ServiceProvider;
import com.example.localservice.repository.ServiceRepository;
import com.example.localservice.repository.ServiceProviderRepository;
import com.example.localservice.dto.ServiceRequestDTO;
import com.example.localservice.dto.ServiceResponseDTO;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ServiceService {

    @Autowired
    private ServiceRepository serviceRepository;

    @Autowired
    private ServiceProviderRepository serviceProviderRepository;

    public ServiceResponseDTO createService(ServiceRequestDTO requestDTO) {
        ServiceProvider provider = serviceProviderRepository.findById(requestDTO.getProviderId())
                .orElseThrow(() -> new RuntimeException("ServiceProvider not found: " + requestDTO.getProviderId()));

        com.example.localservice.model.Service service = new com.example.localservice.model.Service();
        service.setServiceName(requestDTO.getServiceName());
        service.setDescription(requestDTO.getDescription());
        service.setPrice(requestDTO.getPrice());
        service.setServiceProvider(provider);

        com.example.localservice.model.Service saved = serviceRepository.save(service);
        return new ServiceResponseDTO(saved.getId(), saved.getServiceName(), saved.getDescription(),
                saved.getCategory(), saved.getPrice(), saved.getServiceProvider().getId());
    }

    public List<ServiceResponseDTO> getAllServices() {
        return serviceRepository.findAll().stream()
                .map(s -> new ServiceResponseDTO(s.getId(), s.getServiceName(), s.getDescription(),
                        s.getCategory(), s.getPrice(), s.getServiceProvider().getId()))
                .collect(Collectors.toList());
    }

    public com.example.localservice.model.Service findById(Long id) {
        return serviceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Service not found: " + id));
    }
}
