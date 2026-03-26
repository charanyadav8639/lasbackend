package com.example.localservice.controller;

import com.example.localservice.dto.BookingRequestDTO;
import com.example.localservice.dto.BookingResponseDTO;
import com.example.localservice.model.Booking;
import com.example.localservice.model.BookingStatus;
import com.example.localservice.model.Service;
import com.example.localservice.model.ServiceProvider;
import com.example.localservice.model.ServiceWorker;
import com.example.localservice.model.User;
import com.example.localservice.repository.BookingRepository;
import com.example.localservice.repository.ServiceProviderRepository;
import com.example.localservice.repository.ServiceRepository;
import com.example.localservice.repository.ServiceWorkerRepository;
import com.example.localservice.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.transaction.annotation.Transactional;

@RestController
@RequestMapping("/api/bookings")
@Transactional(readOnly = true)
public class BookingController {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ServiceProviderRepository providerRepository;

    @Autowired
    private ServiceRepository serviceRepository;

    @Autowired
    private ServiceWorkerRepository workerRepository;

    @GetMapping
    public List<BookingResponseDTO> getAllBookings(@RequestParam(name = "userId", required = false) Long userId) {
        return bookingRepository.findAll()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @PostMapping
    @Transactional
    public ResponseEntity<BookingResponseDTO> createBooking(@Valid @RequestBody BookingRequestDTO request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        ServiceProvider provider = providerRepository.findById(request.getProviderId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Service provider not found"));

        Service service = serviceRepository.findById(request.getServiceId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Service not found"));

        ServiceWorker worker = null;
        if (request.getWorkerId() != null) {
            worker = workerRepository.findById(request.getWorkerId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Service worker not found"));
        }

        Booking booking = new Booking();
        booking.setUser(user);
        booking.setServiceProvider(provider);
        booking.setService(service);
        booking.setWorker(worker);
        booking.setBookingDate(request.getBookingDate());
        booking.setAddress(request.getAddress());
        booking.setIssueDescription(request.getIssueDescription());
        booking.setStatus(BookingStatus.PENDING);

        Booking saved = bookingRepository.save(booking);
        return ResponseEntity.status(HttpStatus.CREATED).body(toDto(saved));
    }

    @PatchMapping("/{id}/status")
    @Transactional
    public ResponseEntity<BookingResponseDTO> updateStatus(
            @PathVariable(name = "id") Long id,
            @RequestParam(name = "userId", required = false) Long userId,
            @RequestBody Map<String, String> body) {

        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Booking not found"));

        String statusStr = body.get("status");
        if (statusStr == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Status is required");
        }

        BookingStatus newStatus;
        try {
            newStatus = BookingStatus.valueOf(statusStr);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid status value: " + statusStr);
        }

        try {
            booking.setStatus(newStatus);
            Booking saved = bookingRepository.save(booking);
            return ResponseEntity.ok(toDto(saved));
        } catch (Exception e) {
            System.err.println("Error updating booking status: " + e.getMessage());
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to update booking status: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> deleteBooking(@PathVariable(name = "id") Long id, @RequestParam(name = "userId", required = false) Long userId, @RequestParam(name = "isAdmin", required = false) Boolean isAdmin) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Booking not found"));
        
        if (Boolean.TRUE.equals(isAdmin)) {
            bookingRepository.delete(booking);
            return ResponseEntity.noContent().build();
        }

        if (userId == null || !booking.getUser().getId().equals(userId)) {
             throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Not authorized to delete this booking");
        }
        
        bookingRepository.delete(booking);
        return ResponseEntity.noContent().build();
    }

    private BookingResponseDTO toDto(Booking booking) {
        return new BookingResponseDTO(
                booking.getId(),
                booking.getUser() != null ? booking.getUser().getId() : null,
                booking.getServiceProvider() != null ? booking.getServiceProvider().getId() : null,
                booking.getService() != null ? booking.getService().getId() : null,
                booking.getWorker() != null ? booking.getWorker().getId() : null,
                booking.getService() != null ? booking.getService().getServiceName() : null,
                booking.getWorker() != null ? booking.getWorker().getName() : null,
                booking.getBookingDate(),
                booking.getAddress(),
                booking.getIssueDescription(),
                booking.getStatus(),
                booking.getUser() != null ? booking.getUser().getMobileNumber() : null
        );
    }
}
