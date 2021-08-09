package com.alura.challenge.raphaelf.aluraflix.controllers;

import com.alura.challenge.raphaelf.aluraflix.DTOs.VideoInputDTO;
import com.alura.challenge.raphaelf.aluraflix.DTOs.VideoViewDTO;
import com.alura.challenge.raphaelf.aluraflix.controllers.exceptions.StandardError;
import com.alura.challenge.raphaelf.aluraflix.services.VideoService;
import com.alura.challenge.raphaelf.aluraflix.services.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.bind.MethodArgumentNotValidException;

import javax.management.BadAttributeValueExpException;
import javax.validation.ValidationException;
import java.security.InvalidParameterException;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(VideoController.class)
class VideoControllerTest {

    @Autowired
    private VideoController controller;
    @MockBean
    private VideoService service;
    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {

    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void givenVideos_whenGetVideos_thenReturnJsonArray() throws Exception {
        List<VideoViewDTO> videosView = Arrays.asList(
                new VideoViewDTO(1L,"Nome do video","descricao do video","url",1L),
                new VideoViewDTO(2L,"Nome do video 2","descricao do video 2","url2",1L)
        );
        when(service.findAll()).thenReturn(videosView);

        assertThat(controller).isNotNull();
        mockMvc.perform(get("/videos").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].titulo", is(videosView.get(0).getTitulo())));

    }

    @Test
    void givenVideosIsEmpty_whenGetVideos_thenReturnNotFound() throws Exception {
        when(service.findAll()).thenThrow(ResourceNotFoundException.class);

        mockMvc.perform(get("/videos").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

    }

    @Test
    void givenVideoId_whenGetFindVideoById_thenReturnVideo() throws Exception {
        VideoViewDTO videoDto = new VideoViewDTO(1L, "Video teste", "descricao", "url", 1L);
        when(service.findById(1L)).thenReturn(videoDto);

        mockMvc.perform(get("/videos/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("titulo", is(videoDto.getTitulo())));
    }

    @Test
    void givenVideoNotExists_whenGetFindVideoById_thenReturnNotFound() throws Exception {
        VideoViewDTO videoDto = new VideoViewDTO(1L, "Video teste", "descricao", "url", 1L);
        when(service.findById(99L)).thenThrow(ResourceNotFoundException.class);

        mockMvc.perform(get("/videos/99").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void givenValidVideosInput_whenSaveNewVideo_thenReturnCreated() throws Exception {
        when(service.save(any())).thenReturn(new VideoViewDTO(1L, "video teste", "descricao", "http://url.com", 1L));

        String input = "{ \"titulo\": \"Video novo\", " +
                " \"descricao\":\"descricao video\", " +
                " \"url\":\"http://url.com\"}";

        mockMvc.perform(post("/videos")
                        .content(input)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("id").exists())
                .andExpect(jsonPath("titulo").value("video teste"));
    }

    @Test
    void givenInvalidVideo_whenSaveNewVideo_thenReturnValidationException() throws Exception {
        when(service.save(any(VideoInputDTO.class)))
                .thenThrow(ValidationException.class);

        String input = "{ \"titulo\": \"Video novo\", " +
                " \"descricao\":\"descricao video\", " +
                " \"url\":\"teste\"}";

        mockMvc.perform(post("/videos")
                        .content(input)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException))
                .andExpect(jsonPath("error").exists())
                .andExpect(jsonPath("error").value("Validation exception"));
    }

    @Test
    void update() {
    }

    @Test
    void deleteById() {
    }
}