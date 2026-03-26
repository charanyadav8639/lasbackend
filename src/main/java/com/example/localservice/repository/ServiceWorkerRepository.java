package com.example.localservice.repository;

import com.example.localservice.model.ServiceWorker;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ServiceWorkerRepository extends JpaRepository<ServiceWorker, Long> {
    List<ServiceWorker> findByCompanyId(Long companyId);
}
