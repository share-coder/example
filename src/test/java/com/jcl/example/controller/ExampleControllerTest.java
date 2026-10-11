package com.jcl.example.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.jcl.example.dto.ExampleRequest;
import com.jcl.example.dto.ExampleResponse;
import com.jcl.example.service.ExampleService;
import java.time.Instant;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(ExampleController.class)
class ExampleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ExampleService exampleService;

    @Test
    void createReturnsCreatedExample() throws Exception {
        Instant now = Instant.parse("2026-10-11T11:00:00Z");
        when(exampleService.create(any(ExampleRequest.class)))
                .thenReturn(new ExampleResponse(1L, "alpha", now, now));

        mockMvc.perform(post("/examples")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"alpha\"}"))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/examples/1"))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("alpha"));
    }

    @Test
    void getAllReturnsExamples() throws Exception {
        Instant now = Instant.parse("2026-10-11T11:00:00Z");
        when(exampleService.getAll())
                .thenReturn(List.of(new ExampleResponse(1L, "alpha", now, now)));

        mockMvc.perform(get("/examples"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("alpha"));
    }

    @Test
    void getByIdReturnsExample() throws Exception {
        Instant now = Instant.parse("2026-10-11T11:00:00Z");
        when(exampleService.getById(1L)).thenReturn(new ExampleResponse(1L, "alpha", now, now));

        mockMvc.perform(get("/examples/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void updateReturnsUpdatedExample() throws Exception {
        Instant now = Instant.parse("2026-10-11T11:00:00Z");
        when(exampleService.update(eq(1L), any(ExampleRequest.class)))
                .thenReturn(new ExampleResponse(1L, "beta", now, now));

        mockMvc.perform(put("/examples/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"beta\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("beta"));
    }

    @Test
    void deleteReturnsNoContent() throws Exception {
        doNothing().when(exampleService).delete(1L);

        mockMvc.perform(delete("/examples/1")).andExpect(status().isNoContent());
    }
}
