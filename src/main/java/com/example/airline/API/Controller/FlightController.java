package com.example.airline.API.Controller;

import com.example.airline.DTO.FlightDto.*;
import com.example.airline.Services.FlightService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("api/flights")
@RequiredArgsConstructor
@Validated
public class FlightController {

    private final FlightService service;


    @PostMapping
    public ResponseEntity<flightResponse> create(@Valid @RequestBody flightCreateRequest req, UriComponentsBuilder uri) {
        var body = service.create(req);
        var location = uri.path("/api/flights/{id}").buildAndExpand(body.flightId()).toUri();
        return ResponseEntity.created(location).body(body);
    }

    @GetMapping("/{id}")
    public ResponseEntity<flightResponse> get(@PathVariable Long id) {
        return ResponseEntity.ok(service.get(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<flightResponse>  update(@PathVariable Long id, @Valid @RequestBody flightUpdateRequest updateRequest) {
        return ResponseEntity.ok(service.update(id, updateRequest));
    }
}
