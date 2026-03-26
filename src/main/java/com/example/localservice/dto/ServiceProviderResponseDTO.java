package com.example.localservice.dto;

import com.example.localservice.model.ServiceType;
import com.example.localservice.model.ProviderType;


public class ServiceProviderResponseDTO {
    private Long id;
    private String name;
    private ServiceType serviceType;
    private String location;
    private String phone;
    private Boolean availability;
    private Long ownerId;
    private ProviderType providerType;
    private java.util.List<ServiceResponseDTO> services;
    private java.util.List<ServiceWorkerResponseDTO> workers;
    
    // constructors
    public ServiceProviderResponseDTO(Long id, String name, ServiceType serviceType, String location, String phone, Boolean availability, Long ownerId, ProviderType providerType, java.util.List<ServiceResponseDTO> services, java.util.List<ServiceWorkerResponseDTO> workers) {
        this.id = id;
        this.name = name;
        this.serviceType = serviceType;
        this.location = location;
        this.phone = phone;
        this.availability = availability;
        this.ownerId = ownerId;
        this.providerType = providerType;
        this.services = services;
        this.workers = workers;
    }
    
    // getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public ServiceType getServiceType() { return serviceType; }
    public void setServiceType(ServiceType serviceType) { this.serviceType = serviceType; }
    
    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
    
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    
    public Boolean getAvailability() { return availability; }
    public void setAvailability(Boolean availability) { this.availability = availability; }

    public Long getOwnerId() { return ownerId; }
    public void setOwnerId(Long ownerId) { this.ownerId = ownerId; }

    public ProviderType getProviderType() { return providerType; }
    public void setProviderType(ProviderType providerType) { this.providerType = providerType; }

    public java.util.List<ServiceResponseDTO> getServices() { return services; }
    public void setServices(java.util.List<ServiceResponseDTO> services) { this.services = services; }

    public java.util.List<ServiceWorkerResponseDTO> getWorkers() { return workers; }
    public void setWorkers(java.util.List<ServiceWorkerResponseDTO> workers) { this.workers = workers; }
}
