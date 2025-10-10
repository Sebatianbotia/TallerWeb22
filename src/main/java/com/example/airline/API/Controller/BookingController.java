package com.example.airline.API.Controller;

import com.example.airline.DTO.BookingDTO;
import com.example.airline.DTO.PassengerDTO;
import com.example.airline.Services.BookingService;
import com.example.airline.Services.BookingServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
@RestController
@RequestMapping("api/bookings")
@RequiredArgsConstructor
@Validated
public class BookingController {

    private final BookingServiceImpl service;

    @PostMapping
    public ResponseEntity<BookingDTO.bookingResponse> create(@Valid @RequestBody BookingDTO.bookingCreateRequest req, UriComponentsBuilder uri) {
        var body = service.create(req);
        var location = uri.path("/api/bookings/{id}").buildAndExpand(body.id()).toUri();
        return ResponseEntity.created(location).body(body);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookingDTO.bookingResponse> get(@PathVariable Long id) {
        return ResponseEntity.ok(service.get(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

}
