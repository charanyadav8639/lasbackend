package com.example.localservice.dto;

public class ServiceWorkerResponseDTO {
    private Long id;
    private String name;
    private String phone;
    private String specialization;
    private boolean available;
    private Long companyId;

    public ServiceWorkerResponseDTO(Long id, String name, String phone, String specialization, boolean available, Long companyId) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.specialization = specialization;
        this.available = available;
        this.companyId = companyId;
    }

    // getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getSpecialization() { return specialization; }
    public void setSpecialization(String specialization) { this.specialization = specialization; }

    public boolean isAvailable() { return available; }
    public void setAvailable(boolean available) { this.available = available; }

    public Long getCompanyId() { return companyId; }
    public void setCompanyId(Long companyId) { this.companyId = companyId; }
}
