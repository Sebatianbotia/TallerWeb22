package com.example.airline.API.Controller;

import com.example.airline.DTO.PassengerProfileDTO;
import com.example.airline.DTO.TagDTO.*;
import com.example.airline.services.TagService;
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
@RequestMapping("api/tags")
@RequiredArgsConstructor
@Validated
public class TagController {

    private final TagService service;


    @PostMapping
    public ResponseEntity<tagResponse> create(@Valid @RequestBody tagCreateRequest req, UriComponentsBuilder uri) {
        var body = service.create(req);
        var location = uri.path("/api/tags/{id}").buildAndExpand(body.tagId()).toUri();
        return ResponseEntity.created(location).body(body);
    }

    @GetMapping("/{id}")
    public ResponseEntity<tagResponse> get(@PathVariable Long id) {
        return ResponseEntity.ok(service.get(id));
    }

    @GetMapping("/by-name")
    public ResponseEntity<tagResponse> get(@RequestParam String email) {
        return ResponseEntity.ok(service.getByName(email));
    }
    @GetMapping
    public ResponseEntity<Page<tagResponse>> list(@RequestParam(defaultValue = "0") int page,
                                                                                   @RequestParam(defaultValue = "8") int size) {
        var result = service.list(PageRequest.of(page, size, Sort.by("id").ascending()));
        return ResponseEntity.ok(result);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<tagResponse> update(@PathVariable Long id, @Valid @RequestBody tagUpdateRequest updateRequest) {
        return ResponseEntity.ok(service.update(id, updateRequest));
    }

}
