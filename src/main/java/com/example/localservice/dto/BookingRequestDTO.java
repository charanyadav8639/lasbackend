package com.example.localservice.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.FutureOrPresent;
import com.example.localservice.model.BookingStatus;
import java.time.LocalDateTime;

public class BookingRequestDTO {
    @NotNull(message = "User ID is required")
    private Long userId;
    
    @NotNull(message = "Provider ID is required")
    private Long providerId;
    
    @NotNull(message = "Service ID is required")
    private Long serviceId;
    
    private Long workerId; // Optional

    @NotNull(message = "Booking date is required")
    @FutureOrPresent(message = "Booking date must be in future or present")
    private LocalDateTime bookingDate;

    @NotBlank(message = "Address is required")
    private String address;

    private String issueDescription;
    
    @NotNull(message = "Status is required")
    private BookingStatus status = BookingStatus.PENDING;
    
    // getters and setters
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
    
    public Long getProviderId() { return providerId; }
    public void setProviderId(Long providerId) { this.providerId = providerId; }
    
    public Long getServiceId() { return serviceId; }
    public void setServiceId(Long serviceId) { this.serviceId = serviceId; }

    public Long getWorkerId() { return workerId; }
    public void setWorkerId(Long workerId) { this.workerId = workerId; }
    
    public LocalDateTime getBookingDate() { return bookingDate; }
    public void setBookingDate(LocalDateTime bookingDate) { this.bookingDate = bookingDate; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getIssueDescription() { return issueDescription; }
    public void setIssueDescription(String issueDescription) { this.issueDescription = issueDescription; }
    
    public BookingStatus getStatus() { return status; }
    public void setStatus(BookingStatus status) { this.status = status; }
}
