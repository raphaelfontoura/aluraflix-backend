package com.alura.challenge.raphaelf.aluraflix.controllers;

import com.alura.challenge.raphaelf.aluraflix.DTOs.CategoryInputDTO;
import com.alura.challenge.raphaelf.aluraflix.DTOs.CategoryUpdateDTO;
import com.alura.challenge.raphaelf.aluraflix.DTOs.CategoryViewDTO;
import com.alura.challenge.raphaelf.aluraflix.DTOs.VideoViewDTO;
import com.alura.challenge.raphaelf.aluraflix.services.CategoryService;
import com.alura.challenge.raphaelf.aluraflix.services.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CategoryController.class)
class CategoryControllerTest {

    @Autowired
    private CategoryController controller;
    @MockBean
    private CategoryService service;
    @Autowired
    private MockMvc mockMvc;
    private CategoryViewDTO categoryViewDTO;

    @BeforeEach
    void setUp() {
        categoryViewDTO = new CategoryViewDTO(1L, "LIVRE", "#FFF");
    }

    @AfterEach
    void tearDown() {
    }

    @WithMockUser(value = "alurauser")
    @Test
    void givenGetCategories_whenGetAll_thenReturnListCategoryView() throws Exception {
        List<CategoryViewDTO> categories = Arrays.asList(
                categoryViewDTO,
                new CategoryViewDTO(2L, "OUTROS", "#AAA")
        );
        when(service.findAll(any())).thenReturn(new PageImpl<>(categories));

        mockMvc.perform(get("/categorias/").accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("content", hasSize(2)))
                .andExpect(jsonPath("content[0].titulo", is(categoryViewDTO.getTitulo())));

    }

    @Test
    void givenGetCategories_whenUnauthenticated_thenReturnUnauthorized401() throws Exception {
        List<CategoryViewDTO> categories = Arrays.asList(
                categoryViewDTO,
                new CategoryViewDTO(2L, "OUTROS", "#AAA")
        );
        when(service.findAll(any())).thenReturn(new PageImpl<>(categories));

        mockMvc.perform(get("/categorias/").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());

    }

    @WithMockUser(value = "alurauser")
    @Test
    void givenGetCategories_whenEmptyResult_thenReturnException() throws Exception {
        List<CategoryViewDTO> categories = new ArrayList<>();
        when(service.findAll(any())).thenThrow(ResourceNotFoundException.class);

        mockMvc.perform(get("/categorias").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("error").exists());

    }

    @WithMockUser(value = "alurauser")
    @Test
    void givenGetCategory_whenCategoryIdInformed_thenReturnCategoryView() throws Exception {
        when(service.findById(1L)).thenReturn(categoryViewDTO);

        mockMvc.perform(get("/categorias/1").accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("id").value(1))
                .andExpect(jsonPath("titulo").value(categoryViewDTO.getTitulo()));
    }

    @WithMockUser(value = "alurauser")
    @Test
    void givePostCategory_whenCategoryDataIsValid_thenCreateNewCategory() throws Exception {
        when(service.save(any(CategoryInputDTO.class))).thenReturn(categoryViewDTO);
        String input = "{ " +
                " \"titulo\":\"TESTE\", " +
                " \"cor\":\"#123\" " +
                " }";

        mockMvc.perform(post("/categorias")
                        .content(input)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("id").exists());
    }

    @WithMockUser(value = "alurauser")
    @Test
    void givePutCategory_whenCategoryUpdateDtoIsValid_thenUpdateCategoryInformed() throws Exception {
        when(service.update(any(CategoryUpdateDTO.class))).thenReturn(categoryViewDTO);
        String input = "{ " +
                " \"id\":\"2\", " +
                " \"titulo\":\"TESTE\", " +
                " \"cor\":\"#123\" " +
                " }";

        mockMvc.perform(put("/categorias")
                        .content(input)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("id").exists());

    }

    @WithMockUser(value = "alurauser")
    @Test
    void giveDeleteCategory_whenCategoryIdExists_thenRemoveCategory() throws Exception {
        doNothing().when(service).delete(1L);

        mockMvc.perform(delete("/categorias/1"))
                .andExpect(status().isNoContent());
    }

    @WithMockUser(value = "alurauser")
    @Test
    void giveGetVideosByCategory_whenCategoryIdAndVideosExists_thenReturnVideosList() throws Exception {
        List<VideoViewDTO> videos = Arrays.asList(
                new VideoViewDTO(1L, "teste", "descricao", "http://url.com", 1L),
                new VideoViewDTO(1L, "teste", "descricao", "http://url.com", 1L),
                new VideoViewDTO(1L, "teste", "descricao", "http://url.com", 2L)
                );
        when(service.getVideosByCategory(any(), any()))
                .thenReturn(new PageImpl<>(videos.stream().filter(v -> v.getCategoriaId() == 1L).collect(Collectors.toList())));

        mockMvc.perform(get("/categorias/1/videos")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("content", hasSize(2)));
    }
}