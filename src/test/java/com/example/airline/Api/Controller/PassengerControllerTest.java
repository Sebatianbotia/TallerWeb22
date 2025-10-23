package com.example.airline.Api.Controller;

import com.example.airline.API.Controller.PassengerController;
import com.example.airline.services.PassengerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.airline.API.Error.NotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.endsWith;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static com.example.airline.DTO.PassengerDTO.*;
import static com.example.airline.DTO.PassengerProfileDTO.*;


@WebMvcTest(PassengerController.class)
public class PassengerControllerTest {

    @Autowired
    MockMvc mvc;

    @Autowired
    ObjectMapper om;

    @MockitoBean
    PassengerService service;

    @Test
    void createShouldReturn201AndLocation() throws Exception {
        var profile = new passengerProfileCreateRequest("304", "+57");
        var req = new passengerCreateRequest("Jose", "notienet@gmail.com", profile);
        var resp = new passengerResponse(1L, "304", "+57", null);


        when(service.create(any())).thenReturn(resp);

        mvc.perform(post("/api/passengers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(req)))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", endsWith("/api/passengers/1")))
                .andExpect(jsonPath("$.id").value(1L));

        verify(service).create(any());
    }

    @Test
    void getShouldReturn200() throws Exception {
        when(service.get(1L)).thenReturn(new passengerResponse(1L, "jose", "@Notiente", null));

        mvc.perform(get("/api/passengers/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    void getShouldReturn404WhenNotFound() throws Exception {
        when(service.get(99L)).thenThrow(new NotFoundException("PassengerProfile 99 not found"));

        mvc.perform(get("/api/passengers/99"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("PassengerProfile 99 not found"));
    }

    @Test
    void deleteShouldReturn204() throws Exception {
        mvc.perform(delete("/api/passengers/1"))
                .andExpect(status().isNoContent());
        verify(service).delete(1L);
    }
}
