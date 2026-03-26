package com.example.localservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import com.example.localservice.model.ServiceType;

public class ServiceRequestDTO {
    @NotBlank(message = "Service name is required")
    private String serviceName;
    
    private String description;
    
    @NotNull(message = "Price is required")
    @Positive(message = "Price must be positive")
    private Double price;
    
    @NotNull(message = "Category is required")
    private ServiceType category;
    
    @NotNull(message = "Provider ID is required")
    private Long providerId;
    
    // getters and setters
    public String getServiceName() { return serviceName; }
    public void setServiceName(String serviceName) { this.serviceName = serviceName; }
    
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    
    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }
    
    public ServiceType getCategory() { return category; }
    public void setCategory(ServiceType category) { this.category = category; }
    
    public Long getProviderId() { return providerId; }
    public void setProviderId(Long providerId) { this.providerId = providerId; }
}
