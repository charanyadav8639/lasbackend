package com.example.localservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import com.example.localservice.model.ServiceType;
import com.example.localservice.model.ProviderType;


public class ServiceProviderRequestDTO {
    @NotBlank(message = "Name is required")
    private String name;
    
    @NotNull(message = "Service type is required")
    private ServiceType serviceType;
    
    @NotNull(message = "Provider type is required")
    private ProviderType providerType;
    
    @NotBlank(message = "Location is required")
    private String location;
    
    @NotBlank(message = "Phone is required")
    private String phone;
    
    @NotNull(message = "Owner ID is required")
    private Long ownerId;
    
    private Boolean availability;
    
    // getters and setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public ServiceType getServiceType() { return serviceType; }
    public void setServiceType(ServiceType serviceType) { this.serviceType = serviceType; }

    public ProviderType getProviderType() { return providerType; }
    public void setProviderType(ProviderType providerType) { this.providerType = providerType; }
    
    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
    
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    
    public Long getOwnerId() { return ownerId; }
    public void setOwnerId(Long ownerId) { this.ownerId = ownerId; }
    
    public Boolean getAvailability() { return availability; }
    public void setAvailability(Boolean availability) { this.availability = availability; }
}
