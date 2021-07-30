package com.alura.challenge.raphaelf.aluraflix.services;

import com.alura.challenge.raphaelf.aluraflix.DTOs.*;
import com.alura.challenge.raphaelf.aluraflix.entities.Category;
import com.alura.challenge.raphaelf.aluraflix.entities.Video;
import com.alura.challenge.raphaelf.aluraflix.repositories.CategoryRepository;
import com.alura.challenge.raphaelf.aluraflix.repositories.VideoRepository;
import com.alura.challenge.raphaelf.aluraflix.services.exceptions.DatabaseException;
import com.alura.challenge.raphaelf.aluraflix.services.exceptions.ResourceNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class VideoService {
    @Autowired
    private VideoRepository repository;
    @Autowired
    private CategoryRepository categoryRepository;

    public List<VideoViewDTO> findAll() {
        var videos = repository.findAll();
        if (videos.isEmpty()) throw new ResourceNotFoundException("Não existem registros na base de dados.");
        return videos.stream().map(VideoViewDTO::new).collect(Collectors.toList());
    }

    public VideoViewDTO findById(Long id) {
        var video = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Video não encontrado."));
        return new VideoViewDTO(video);
    }

    @Transactional
    public VideoViewDTO save(VideoInputDTO videoDto) {
        var video = Video.builder()
                .titulo(videoDto.getTitulo())
                .descricao(videoDto.getDescricao())
                .url(videoDto.getUrl())
                .category(categoryRepository.getById(
                        hasCategoryId(videoDto) ? videoDto.getCategoriaId() : 1L)
                )
                .build();
        return new VideoViewDTO(repository.save(video));
    }

    @Transactional
    public VideoViewDTO update(VideoUpdateDTO dto) {
        try {
            Video video = mapper(dto);
            return new VideoViewDTO(repository.save(video));
        } catch (EntityNotFoundException err) {
            throw new ResourceNotFoundException("Vídeo não encontrado. Id do vídeo: " + dto.getId());
        }
    }

    @Transactional
    public void delete(Long id) {
        try {
            repository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException("Video com id " + id + " não encontrado.");
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Violação de integridade da base de dados.");
        }
    }

    private boolean hasCategoryId (VideoInputDTO input) {
        return input.getCategoriaId() != null;
    }

    private Video mapper(VideoUpdateDTO dto) {
        Video video = repository.findById(dto.getId()).orElseThrow(() ->
                new ResourceNotFoundException("Id não encontrado em categoria")
        );
        if (dto.getTitulo() != null) video.setTitulo(dto.getTitulo());
        if (dto.getDescricao() != null) video.setDescricao(dto.getDescricao());
        if (dto.getUrl() != null) video.setUrl(dto.getUrl());
        if (dto.getCategoriaId() != null) {
            Category category = categoryRepository.getById(dto.getId());
            video.setCategory(category);
        }

        return video;
    }
}
