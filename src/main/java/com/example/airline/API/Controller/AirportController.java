package com.example.airline.API.Controller;

import com.example.airline.DTO.AirportDTO;
import com.example.airline.services.AirportService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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

    @GetMapping
    public ResponseEntity<Page<AirportDTO.AirportResponse>> list(@RequestParam (defaultValue = "0") int page,
                                                                    @RequestParam (defaultValue= "10")int size){
        var result= service.list(PageRequest.of(page, size, Sort.by("id").ascending()));
        return ResponseEntity.ok(result);
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
