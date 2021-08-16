package com.alura.challenge.raphaelf.aluraflix.services;

import com.alura.challenge.raphaelf.aluraflix.DTOs.VideoViewDTO;
import com.alura.challenge.raphaelf.aluraflix.entities.Category;
import com.alura.challenge.raphaelf.aluraflix.entities.Video;
import com.alura.challenge.raphaelf.aluraflix.repositories.VideoRepository;
import com.alura.challenge.raphaelf.aluraflix.services.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest
class VideoServiceTest {
    @Autowired
    private VideoService service;
    private Video video;
    private Category category;
    @MockBean
    private VideoRepository repository;

    @BeforeEach
    void setUp() {
        category = new Category(1L, "LIVRE","#FFF", null);
        video = new Video(1L,"Teste","teste descricao","http://teste.com",category);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void giveFindByTitulo_whenVideoWithTituloExistsIgnoreCase_thenReturnVideo() {

        when(repository.findByTituloContainingIgnoreCase(anyString()))
                .thenReturn(new ArrayList<>(List.of(
                        video,
                        new Video(2L, "Teste 2", "teste descricao 2", "http://teste.com/2", category)
                )));
        List<VideoViewDTO> byTitulo = service.findByTitulo("teste");

        assertFalse(byTitulo.isEmpty());
        assertEquals(2, byTitulo.size());

        assertEquals("Teste", byTitulo.get(0).getTitulo());
    }

    @Test
    void giveFindByTitulo_whenVideoWithTituloNotExists_thenReturnNotFoundException() {

        when(repository.findByTituloContainingIgnoreCase(anyString())).thenThrow(ResourceNotFoundException.class);

        assertThrows(ResourceNotFoundException.class, () -> service.findByTitulo("teste"));
    }
}