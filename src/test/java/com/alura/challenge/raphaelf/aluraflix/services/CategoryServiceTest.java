package com.alura.challenge.raphaelf.aluraflix.services;

import com.alura.challenge.raphaelf.aluraflix.DTOs.VideoViewDTO;
import com.alura.challenge.raphaelf.aluraflix.entities.Category;
import com.alura.challenge.raphaelf.aluraflix.entities.Video;
import com.alura.challenge.raphaelf.aluraflix.repositories.CategoryRepository;
import com.alura.challenge.raphaelf.aluraflix.services.exceptions.ResourceNotFoundException;
import org.checkerframework.checker.units.qual.C;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
class CategoryServiceTest {

    @Autowired
    private CategoryService service;
    @MockBean
    private CategoryRepository repository;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void givenGetVideosByCategory_whenVideosExists_thenReturnListOfVideos() {
        Category category = new Category("LIVRE","#FFF");
        List<Video>  videos = category.getVideos();
        videos.add(new Video(1L, "Titulo", "Descricao", "http://url.com", category));
        when(repository.getById(any())).thenReturn(category);
        List<VideoViewDTO> videosByCategory = service.getVideosByCategory(1L);

        assertEquals(1, videosByCategory.size());
        assertEquals("Titulo", videos.get(0).getTitulo());
    }

    @Test
    void givenGetVideosByCategory_whenVideosNotExists_thenReturnResourceNotFoundException() {

        when(repository.getById(any())).thenThrow(ResourceNotFoundException.class);

        assertThrows(ResourceNotFoundException.class, () -> service.getVideosByCategory(99L));
    }
}