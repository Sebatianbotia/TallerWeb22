package com.example.airline.Api.Controller;

import com.example.airline.API.Controller.SeatInventoryController;
import com.example.airline.API.Controller.TagController;
import com.example.airline.Services.SeatInventoryService;
import com.example.airline.Services.TagService;
import com.example.airline.entities.Cabin;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.airline.API.Error.NotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static com.example.airline.DTO.SeatInventoryDTO.*;

@WebMvcTest(SeatInventoryController.class)
public class SeatInventoryControllerTest {

    @Autowired
    MockMvc mvc;
    @Autowired
    ObjectMapper om;
    @MockitoBean
    SeatInventoryService service;

    @Test
    void createShouldAndReturn201AndLocation() throws Exception {
        var req = new seatInventoryCreateRequest(2, 1, Cabin.ECONOMY);
        var resp = new seatInventoryDtoResponse(2L,2, 1, Cabin.ECONOMY, "holita");

        when(service.create(any())).thenReturn(resp);

        mvc.perform(post("/api/seatInventories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(req))).andExpect(status().isCreated())
                .andExpect(header().string("Location", org.hamcrest.Matchers.endsWith("/api/seatInventories/2")))
                .andExpect(jsonPath("$.seatInventoryId").value(2L));

    }

    @Test
    void getShouldAndReturn200() throws Exception{
        when(service.get(2L)).thenReturn(new seatInventoryDtoResponse(2L,2, 1, Cabin.ECONOMY, "holita"));
        mvc.perform(get("/api/seatInventories/2")).andExpect(status().isOk())
                .andExpect(jsonPath("$.seatInventoryId").value(2L));
    }

    @Test
    void get_shouldReturn404WhenNotFound() throws Exception{
        when(service.get(5L)).thenThrow(new NotFoundException("SeatInventory with ID: 5L not found"));

        mvc.perform(get("/api/seatInventories/5"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("SeatInventory with ID: 5L not found"));
    }

    @Test
    void deleteShouldReturn204() throws Exception {
        mvc.perform(delete("/api/seatInventories/5"))
                .andExpect(status().isNoContent());
        verify(service).delete(5L);
    }
}
