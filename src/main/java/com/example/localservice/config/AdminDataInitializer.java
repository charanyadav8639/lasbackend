package com.example.localservice.config;

import com.example.localservice.model.Role;
import com.example.localservice.model.User;
import com.example.localservice.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AdminDataInitializer {

    @Bean
    CommandLineRunner initDatabase(UserRepository userRepository) {
        return args -> {
            String adminEmail = "charanyadav863931@gmail.com";
            if (userRepository.findByEmail(adminEmail).isEmpty()) {
                User admin = new User();
                admin.setName("Master Admin");
                admin.setEmail(adminEmail);
                admin.setPassword("charan123");
                admin.setRole(Role.ADMIN);
                userRepository.save(admin);
                System.out.println("Generated Master Admin account: " + adminEmail);
            }
        };
    }
}
