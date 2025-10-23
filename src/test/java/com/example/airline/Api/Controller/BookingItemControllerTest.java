package com.example.airline.Api.Controller;

import com.example.airline.API.Controller.BookingItemController;
import com.example.airline.DTO.BookingItemDTO;
import com.example.airline.services.BookingItemService;
import com.example.airline.entities.Cabin;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.airline.API.Error.NotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(BookingItemController.class)
class BookingItemControllerTest {
    @Autowired
    MockMvc mvc;
    @Autowired
    ObjectMapper om;
    @MockitoBean
    BookingItemService service;
    @Test
    void createShouldAndReturn201AndLocation() throws Exception {
        var req = new BookingItemDTO.bookingItemCreateRequest(new BigDecimal(13),2, Cabin.ECONOMY, 12L, 14L);
        var resp = new BookingItemDTO.bookingItemReponse(13L, Cabin.ECONOMY, 12L, null);

        when(service.create(any())).thenReturn(resp);

        mvc.perform(post("/api/bookingItems")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(req))).andExpect(status().isCreated())
                .andExpect(header().string("Location", org.hamcrest.Matchers.endsWith("/api/bookingItems/13")))
                .andExpect(jsonPath("$.bookingitemsId").value(13));

    }

    @Test
    void getShouldAndReturn200() throws Exception{
        when(service.get(13L)).thenReturn( new BookingItemDTO.bookingItemReponse(13L, Cabin.ECONOMY, 12L, null));
        mvc.perform(get("/api/bookingItems/13")).andExpect(status().isOk())
                .andExpect(jsonPath("$.bookingitemsId").value(13L));
    }
    @Test
    void get_shouldReturn404WhenNotFound() throws Exception {
        when(service.get(69L)).thenThrow(new NotFoundException("BookingItem 99 not found"));

        mvc.perform(get("/api/bookingItems/69"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("BookingItem 99 not found"));
    }

    @Test
    void delete_shouldReturn204() throws Exception {
        mvc.perform(delete("/api/bookingItems/3"))
                .andExpect(status().isNoContent());
        verify(service).delete(3L);
    }
}