package com.alura.challenge.raphaelf.aluraflix.services;

import com.alura.challenge.raphaelf.aluraflix.DTOs.VideoViewDTO;
import com.alura.challenge.raphaelf.aluraflix.entities.Category;
import com.alura.challenge.raphaelf.aluraflix.entities.Video;
import com.alura.challenge.raphaelf.aluraflix.repositories.CategoryRepository;
import com.alura.challenge.raphaelf.aluraflix.services.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class CategoryServiceTest {

    @Autowired
    private CategoryService service;
    @MockBean
    private CategoryRepository repository;
    private PageRequest pageRequest;

    @BeforeEach
    void setUp() {
        pageRequest = PageRequest.of(0,5);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void givenGetVideosByCategory_whenVideosExists_thenReturnListOfVideos() {
        Category category = new Category("LIVRE","#FFF");
        List<Video> videos = category.getVideos();
        videos.add(new Video(1L, "Titulo", "Descricao", "http://url.com", category));
        when(repository.findById(any())).thenReturn(Optional.of(category));

        Page<VideoViewDTO> videosByCategory = service.getVideosByCategory(1L, pageRequest);

        verify(repository, times(1)).findById(1L);
        assertEquals(1, videosByCategory.getTotalElements());
        assertEquals("Titulo", videos.get(0).getTitulo());
    }

    @Test
    void givenGetVideosByCategory_whenVideosNotExists_thenReturnResourceNotFoundException() {

        when(repository.findById(any())).thenThrow(ResourceNotFoundException.class);

        assertThrows(ResourceNotFoundException.class, () -> service.getVideosByCategory(99L, pageRequest));
    }
}