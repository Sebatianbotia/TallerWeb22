package com.example.airline.API.Controller;

import com.example.airline.DTO.AirlaneDTO;
import com.example.airline.services.AirlineService;
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
@RequestMapping("api/airlines")
@RequiredArgsConstructor
@Validated
public class AirlineController {

    private final AirlineService service;

    @PostMapping
    public ResponseEntity<AirlaneDTO.airlineResponse> create(@Valid @RequestBody AirlaneDTO.airlineCreateRequest req, UriComponentsBuilder uri) {
        var body = service.create(req);
        var location = uri.path("/api/airlines/{id}").buildAndExpand(body.id()).toUri();
        return ResponseEntity.created(location).body(body);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AirlaneDTO.airlineResponse> get(@PathVariable Long id) {
        return ResponseEntity.ok(service.get(id));

    }
    @GetMapping
    public ResponseEntity<Page<AirlaneDTO.airlineResponse>> list(@RequestParam(defaultValue = "0") int page,
                                                                 @RequestParam(defaultValue = "9") int size) {
        var result = service.list(PageRequest.of(page, size, Sort.by("id").ascending()));
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<AirlaneDTO.airlineResponse>  update(@PathVariable Long id, @Valid @RequestBody AirlaneDTO.airlineUpdateRequest updateRequest) {
        return ResponseEntity.ok(service.update(id, updateRequest));
    }
}
