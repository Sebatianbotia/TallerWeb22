package com.example.airline.API.Controller;

import com.example.airline.DTO.SeatInventoryDTO.*;
import com.example.airline.services.SeatInventoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("api/seatInventories")
@RequiredArgsConstructor
@Validated
public class SeatInventoryController {

    private final SeatInventoryService service;


    @PostMapping
    public ResponseEntity<seatInventoryDtoResponse> create(@Valid @RequestBody seatInventoryCreateRequest req, UriComponentsBuilder uri) {
        var body = service.create(req);
        var location = uri.path("/api/seatInventories/{id}").buildAndExpand(body.seatInventoryId()).toUri();
        return ResponseEntity.created(location).body(body);
    }

    @GetMapping("/{id}")
    public ResponseEntity<seatInventoryDtoResponse> get(@PathVariable Long id) {
        return ResponseEntity.ok(service.get(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<seatInventoryDtoResponse>  update(@PathVariable Long id, @Valid @RequestBody seatInventoryUpdateRequest updateRequest) {
        return ResponseEntity.ok(service.update(id, updateRequest));
    }
}
