package com.example.localservice.dto;

import com.example.localservice.model.BookingStatus;
import java.time.LocalDateTime;

public class BookingResponseDTO {
    private Long id;
    private Long userId;
    private Long providerId;
    private Long serviceId;
    private Long workerId;
    private String serviceName;
    private String workerName;
    private LocalDateTime bookingDate;
    private String address;
    private String issueDescription;
    private BookingStatus status;
    private String customerMobileNumber;
    
    // constructors
    public BookingResponseDTO(Long id, Long userId, Long providerId, Long serviceId, Long workerId,
                              String serviceName, String workerName,
                              LocalDateTime bookingDate, String address, String issueDescription, BookingStatus status,
                              String customerMobileNumber) {
        this.id = id;
        this.userId = userId;
        this.providerId = providerId;
        this.serviceId = serviceId;
        this.workerId = workerId;
        this.serviceName = serviceName;
        this.workerName = workerName;
        this.bookingDate = bookingDate;
        this.address = address;
        this.issueDescription = issueDescription;
        this.status = status;
        this.customerMobileNumber = customerMobileNumber;
    }
    
    // getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
    
    public Long getProviderId() { return providerId; }
    public void setProviderId(Long providerId) { this.providerId = providerId; }
    
    public Long getServiceId() { return serviceId; }
    public void setServiceId(Long serviceId) { this.serviceId = serviceId; }

    public Long getWorkerId() { return workerId; }
    public void setWorkerId(Long workerId) { this.workerId = workerId; }

    public String getServiceName() { return serviceName; }
    public void setServiceName(String serviceName) { this.serviceName = serviceName; }

    public String getWorkerName() { return workerName; }
    public void setWorkerName(String workerName) { this.workerName = workerName; }
    
    public LocalDateTime getBookingDate() { return bookingDate; }
    public void setBookingDate(LocalDateTime bookingDate) { this.bookingDate = bookingDate; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getIssueDescription() { return issueDescription; }
    public void setIssueDescription(String issueDescription) { this.issueDescription = issueDescription; }
    
    public BookingStatus getStatus() { return status; }
    public void setStatus(BookingStatus status) { this.status = status; }
    
    public String getCustomerMobileNumber() { return customerMobileNumber; }
    public void setCustomerMobileNumber(String customerMobileNumber) { this.customerMobileNumber = customerMobileNumber; }
}
