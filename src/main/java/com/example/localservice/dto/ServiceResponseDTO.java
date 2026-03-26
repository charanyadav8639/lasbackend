package com.example.localservice.dto;

import com.example.localservice.model.ServiceType;

public class ServiceResponseDTO {
    private Long id;
    private String serviceName;
    private String description;
    private ServiceType category;
    private Double price;
    private Long providerId;
    
    // constructors
    public ServiceResponseDTO(Long id, String serviceName, String description, ServiceType category, Double price, Long providerId) {
        this.id = id;
        this.serviceName = serviceName;
        this.description = description;
        this.category = category;
        this.price = price;
        this.providerId = providerId;
    }
    
    // getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getServiceName() { return serviceName; }
    public void setServiceName(String serviceName) { this.serviceName = serviceName; }
    
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    
    public ServiceType getCategory() { return category; }
    public void setCategory(ServiceType category) { this.category = category; }
    
    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }
    
    public Long getProviderId() { return providerId; }
    public void setProviderId(Long providerId) { this.providerId = providerId; }
}
