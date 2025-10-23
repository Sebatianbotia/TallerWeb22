package com.example.airline.Api.Controller;

import com.example.airline.API.Controller.AirlineController;
import com.example.airline.DTO.AirlaneDTO;
import com.example.airline.services.AirlineService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.airline.API.Error.NotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AirlineController.class)
class AirlineControllerTest {
    @Autowired
    MockMvc mvc;
    @Autowired
    ObjectMapper om;
    @MockitoBean
    AirlineService service;
    @Test
    void createShouldAndReturn201AndLocation() throws Exception {
        var req = new AirlaneDTO.airlineCreateRequest("JoseAirport", "12JL");
        var resp = new AirlaneDTO.airlineResponse(12L, "JoseAirport", "12JL", null);

        when(service.create(any())).thenReturn(resp);

        mvc.perform(post("/api/airlines")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(req))).andExpect(status().isCreated())
                .andExpect(header().string("Location", org.hamcrest.Matchers.endsWith("/api/airlines/12")))
                .andExpect(jsonPath("$.id").value(12));

    }

    @Test
    void getShouldAndReturn200() throws Exception{
        when(service.get(5L)).thenReturn(new AirlaneDTO.airlineResponse(5L,"Name", "123", null));
        mvc.perform(get("/api/airlines/5")).andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(5));
    }
    @Test
    void get_shouldReturn404WhenNotFound() throws Exception {
        when(service.get(69L)).thenThrow(new NotFoundException("Member 99 not found"));

        mvc.perform(get("/api/airlines/69"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Member 99 not found"));
    }

    @Test
    void delete_shouldReturn204() throws Exception {
        mvc.perform(delete("/api/airlines/3"))
                .andExpect(status().isNoContent());
        verify(service).delete(3L);
    }

}