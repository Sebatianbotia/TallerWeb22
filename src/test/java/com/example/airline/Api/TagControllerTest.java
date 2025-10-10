package com.example.airline.Api;

import com.example.airline.API.Controller.TagController;
import com.example.airline.Services.TagService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.dockerjava.api.exception.NotFoundException;
import jakarta.persistence.EntityNotFoundException;
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
import static com.example.airline.DTO.TagDTO.*;

@WebMvcTest(TagController.class)
public class TagControllerTest {
    @Autowired
    MockMvc mvc;
    @Autowired
    ObjectMapper om;
    @MockitoBean
    TagService service;

    @Test
    void createShouldAndReturn201AndLocation() throws Exception {
        var req = new tagCreateRequest("Name");
        var resp = new tagResponse(2L,"Name");

        when(service.create(any())).thenReturn(resp);

        mvc.perform(post("/api/tags")
                .contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(req))).andExpect(status().isCreated())
                .andExpect(header().string("Location", org.hamcrest.Matchers.endsWith("/api/tags/2")))
                .andExpect(jsonPath("$.tagId").value(2L));

    }

    @Test
    void getShouldAndReturn200() throws Exception{
        when(service.get(5L)).thenReturn(new tagResponse(2L,"Name"));
        mvc.perform(get("/api/tags/5")).andExpect(status().isOk())
                .andExpect(jsonPath("$.tagId").value(2L));
    }

    @Test
    void get_shouldReturn404WhenNotFound() throws Exception{
        when(service.get(5L)).thenReturn(new NotFoundException("Tag with 5L"));
    }
}
