package com.example.localservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.localservice.model.ServiceProvider;
import java.util.List;

@Repository
public interface ServiceProviderRepository extends JpaRepository<ServiceProvider, Long> {
    List<ServiceProvider> findByServiceType(com.example.localservice.model.ServiceType serviceType);
    List<ServiceProvider> findByAvailabilityTrue();
}
