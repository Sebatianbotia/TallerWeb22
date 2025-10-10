package com.example.airline.API.Controller;

import com.example.airline.DTO.BookingDTO;
import com.example.airline.DTO.BookingItemDTO;
import com.example.airline.DTO.PassengerDTO;
import com.example.airline.Services.BookingItemServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("api/bookingItems")
@RequiredArgsConstructor
@Validated
public class BookingItemController {
    private final BookingItemServiceImpl service;


    @PostMapping
    public ResponseEntity<BookingItemDTO.bookingItemReponse> create(@Valid @RequestBody BookingItemDTO.bookingItemCreateRequest req, UriComponentsBuilder uri) {
        var body = service.create(req);
        var location = uri.path("/api/bookingItems/{id}").buildAndExpand(body.bookingitemsId()).toUri();
        return ResponseEntity.created(location).body(body);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookingItemDTO.bookingItemReponse> get(@PathVariable Long id) {
        return ResponseEntity.ok(service.get(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<BookingItemDTO.bookingItemReponse>  update(@PathVariable Long id, @Valid @RequestBody BookingItemDTO.bookingItemUpdateRequest updateRequest) {
        return ResponseEntity.ok(service.update(id, updateRequest));
    }

}
