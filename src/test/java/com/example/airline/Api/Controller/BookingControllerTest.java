package com.example.airline.Api.Controller;

import com.example.airline.API.Controller.AirlineController;
import com.example.airline.API.Controller.BookingController;
import com.example.airline.DTO.AirlaneDTO;
import com.example.airline.DTO.BookingDTO;
import com.example.airline.Services.AirlineService;
import com.example.airline.Services.BookingService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.airline.API.Error.NotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BookingController.class)

class BookingControllerTest {
    @Autowired
    MockMvc mvc;
    @Autowired
    ObjectMapper om;
    @MockitoBean
    BookingService service;
    @Test
    void createShouldAndReturn201AndLocation() throws Exception {
        var req = new BookingDTO.bookingCreateRequest(12L, null);
        var resp = new BookingDTO.bookingResponse(13L, null, null,null );

        when(service.create(any())).thenReturn(resp);

        mvc.perform(post("/api/bookings")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(req))).andExpect(status().isCreated())
                .andExpect(header().string("Location", org.hamcrest.Matchers.endsWith("/api/bookings/13")))
                .andExpect(jsonPath("$.id").value(13));

    }

    @Test
    void getShouldAndReturn200() throws Exception{
        when(service.get(5L)).thenReturn( new BookingDTO.bookingResponse(5L, null, null,null ));
        mvc.perform(get("/api/bookings/5")).andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(5L));
    }
    @Test
    void get_shouldReturn404WhenNotFound() throws Exception {
        when(service.get(69L)).thenThrow(new NotFoundException("Booking 99 not found"));

        mvc.perform(get("/api/bookings/69"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Booking 99 not found"));
    }

    @Test
    void delete_shouldReturn204() throws Exception {
        mvc.perform(delete("/api/bookings/3"))
                .andExpect(status().isNoContent());
        verify(service).delete(3L);
    }

}