package com.example.airline.API.Controller;

import com.example.airline.DTO.PassengerProfileDTO.*;
import com.example.airline.services.PassengerProfileService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("api/passengerProfiles")
@RequiredArgsConstructor
@Validated
public class PassengerProfileController {

    private final PassengerProfileService service;


    @PostMapping
    public ResponseEntity<passengerProfileResponse> create(@Valid @RequestBody  passengerProfileCreateRequest req, UriComponentsBuilder uri) {
        var body = service.create(req);
        var location = uri.path("/api/passengerProfiles/{id}").buildAndExpand(body.passengerProfileID()).toUri();
        return ResponseEntity.created(location).body(body);
    }

    @GetMapping("/{id}")
    public ResponseEntity<passengerProfileResponse> get(@PathVariable Long id) {
        return ResponseEntity.ok(service.get(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<passengerProfileResponse>  update(@PathVariable Long id, @Valid @RequestBody passengerProfileUpdateRequest updateRequest) {
        return ResponseEntity.ok(service.update(id, updateRequest));
    }
}
