package com.example.airline.Api.Controller;


import com.example.airline.API.Controller.FlightController;
import com.example.airline.API.Controller.TagController;
import com.example.airline.DTO.FlightDto;
import com.example.airline.Services.FlightService;
import com.example.airline.Services.TagService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.airline.API.Error.NotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(FlightController.class)
public class FlightControllerTest {
    @Autowired
    MockMvc mvc;
    @Autowired
    ObjectMapper om;
    @MockitoBean
    FlightService service;

    @Test
    void createShouldAndReturn201AndLocation() throws Exception {
        var req = new FlightDto.flightCreateRequest("22222", OffsetDateTime.now(), OffsetDateTime.now(), 2L, "origen", "destino",
                new HashSet<String>(Set.of("economico")), List.of());
        var resp = new FlightDto.flightResponse(2L, "22222", OffsetDateTime.now(), OffsetDateTime.now(), null, null, null, null, null);

        when(service.create(any())).thenReturn(resp);

        mvc.perform(post("/api/flights")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(req))).andExpect(status().isCreated())
                .andExpect(header().string("Location", org.hamcrest.Matchers.endsWith("/api/flights/2")))
                .andExpect(jsonPath("$.flightId").value(2L));

    }

    @Test
    void getShouldAndReturn200() throws Exception{
        when(service.get(2L)).thenReturn(new FlightDto.flightResponse(2L, "2", OffsetDateTime.now(), OffsetDateTime.now(), null, null, null, null, null));
        mvc.perform(get("/api/flights/2")).andExpect(status().isOk())
                .andExpect(jsonPath("$.flightId").value(2L));
    }

    @Test
    void get_shouldReturn404WhenNotFound() throws Exception{
        when(service.get(5L)).thenThrow(new NotFoundException("flight with ID: 5L not found"));

        mvc.perform(get("/api/flights/5"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("flight with ID: 5L not found"));
    }

    @Test
    void deleteShouldReturn204() throws Exception {
        mvc.perform(delete("/api/flights/5"))
                .andExpect(status().isNoContent());
        verify(service).delete(5L);
    }
}
