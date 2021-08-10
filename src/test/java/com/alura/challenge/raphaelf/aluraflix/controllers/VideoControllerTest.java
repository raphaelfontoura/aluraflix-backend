package com.alura.challenge.raphaelf.aluraflix.controllers;

import com.alura.challenge.raphaelf.aluraflix.DTOs.VideoInputDTO;
import com.alura.challenge.raphaelf.aluraflix.DTOs.VideoUpdateDTO;
import com.alura.challenge.raphaelf.aluraflix.DTOs.VideoViewDTO;
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
import org.springframework.web.bind.MethodArgumentNotValidException;

import javax.validation.ValidationException;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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
    private VideoViewDTO videoViewDTO;

    @BeforeEach
    void setUp() {
        videoViewDTO = new VideoViewDTO(1L,"Nome do video","descricao do video","http://url.com",1L);

    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void givenVideos_whenGetVideos_thenReturnJsonArray() throws Exception {
        List<VideoViewDTO> videosView = Arrays.asList(
                videoViewDTO,
                new VideoViewDTO(2L,"Nome do video 2","descricao do video 2","url2",1L)
        );
        when(service.findAll()).thenReturn(videosView);

        assertThat(controller).isNotNull();
        mockMvc.perform(get("/videos").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].titulo", is(videoViewDTO.getTitulo())));

    }

    @Test
    void givenVideosIsEmpty_whenGetVideos_thenReturnNotFound() throws Exception {
        when(service.findAll()).thenThrow(ResourceNotFoundException.class);

        mockMvc.perform(get("/videos").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

    }

    @Test
    void givenVideoId_whenGetFindVideoById_thenReturnVideo() throws Exception {
        when(service.findById(1L)).thenReturn(videoViewDTO);

        mockMvc.perform(get("/videos/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("titulo", is(videoViewDTO.getTitulo())));
    }

    @Test
    void givenVideoNotExists_whenGetFindVideoById_thenReturnNotFound() throws Exception {
        when(service.findById(99L)).thenThrow(ResourceNotFoundException.class);

        mockMvc.perform(get("/videos/99").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void givenValidVideosInput_whenSaveNewVideo_thenReturnCreated() throws Exception {
        when(service.save(any(VideoInputDTO.class))).thenReturn(videoViewDTO);

        String input = "{ \"titulo\": \"Video novo\", " +
                " \"descricao\":\"descricao video\", " +
                " \"url\":\"http://url.com\"}";

        mockMvc.perform(post("/videos")
                        .content(input)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("id").exists())
                .andExpect(jsonPath("titulo").value(videoViewDTO.getTitulo()));
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
                .andExpect(jsonPath("error").value("Validation exception"))
                .andExpect(jsonPath("$.errors[*]['fieldName']").value("url"));
    }

    @Test
    void givenVideoUpdate_whenUpdateDatas_thenReturnOkAndVideoViewDto() throws Exception {
        when(service.update(any(VideoUpdateDTO.class))).thenReturn(videoViewDTO);
        String input = "{\"id\":1, \"titulo\": \"Video novo\", " +
                " \"descricao\":\"altera descricao video\", " +
                " \"url\":\"http://url.com.br\"}";

        mockMvc.perform(put("/videos")
                        .content(input)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").exists());
    }

    @Test
    void givenVideoUpdate_whenUpdateVideoWithoutId_thenReturnException() throws Exception {
        when(service.update(any(VideoUpdateDTO.class))).thenReturn(videoViewDTO);
        String input = "{\"titulo\": \"Video novo\", " +
                " \"descricao\":\"altera descricao video\", " +
                " \"url\":\"http://url.com.br\"}";

        mockMvc.perform(put("/videos")
                        .content(input)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException))
                .andExpect(jsonPath("error").exists())
                .andExpect(jsonPath("error").value("Validation exception"));
    }

    @Test
    void givenVideoDeleteById_thenReturnNoContent() throws Exception {
        doNothing().when(service).delete(1L);

        mockMvc.perform(delete("/videos/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void givenVideoDeleteByIdInvalid_thenReturnException() throws Exception {
        doThrow(ResourceNotFoundException.class).when(service).delete(99L);

        mockMvc.perform(delete("/videos/99"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("error").value("Resource not found"));
    }
}