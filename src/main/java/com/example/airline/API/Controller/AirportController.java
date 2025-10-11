package com.example.airline.API.Controller;

import com.example.airline.DTO.AirportDTO;
import com.example.airline.DTO.PassengerDTO;
import com.example.airline.Services.AirportService;
import com.example.airline.Services.AirportServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("api/airports")
@RequiredArgsConstructor
@Validated
public class AirportController {
    private final AirportService service;

    @PostMapping
    public ResponseEntity<AirportDTO.AirportResponse> create(@Valid @RequestBody AirportDTO.AirportCreateRequest req, UriComponentsBuilder uri) {
        var body = service.create(req);
        var location = uri.path("/api/airports/{id}").buildAndExpand(body.id()).toUri();
        return ResponseEntity.created(location).body(body);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AirportDTO.AirportResponse> get(@PathVariable Long id) {
        return ResponseEntity.ok(service.get(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<AirportDTO.AirportResponse>  update(@PathVariable Long id, @Valid @RequestBody AirportDTO.AirportUpdateRequest updateRequest) {
        return ResponseEntity.ok(service.update(id, updateRequest));
    }
}
