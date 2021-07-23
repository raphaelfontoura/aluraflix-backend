package com.alura.challenge.raphaelf.aluraflix.services;

import com.alura.challenge.raphaelf.aluraflix.DTOs.VideoInputDTO;
import com.alura.challenge.raphaelf.aluraflix.DTOs.VideoViewDTO;
import com.alura.challenge.raphaelf.aluraflix.entities.Video;
import com.alura.challenge.raphaelf.aluraflix.repositories.VideoRepository;
import com.alura.challenge.raphaelf.aluraflix.services.exceptions.DatabaseException;
import com.alura.challenge.raphaelf.aluraflix.services.exceptions.ResourceNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class VideoService {
    @Autowired
    private VideoRepository repository;

    public List<VideoViewDTO> findAll() {
        var videos = repository.findAll();
        if (videos.isEmpty()) throw new ResourceNotFoundException("Não existem registros na base de dados.");
        return videos.stream().map(VideoViewDTO::new).collect(Collectors.toList());
    }

    public VideoViewDTO findById(Long id) {
        var video = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Video não encontrado."));
        return new VideoViewDTO(video);
    }

    public VideoViewDTO save(VideoInputDTO videoDto) {
        var video = Video.builder()
                .titulo(videoDto.getTitulo())
                .descricao(videoDto.getDescricao())
                .url(videoDto.getUrl())
                .build();
        return new VideoViewDTO(repository.save(video));
    }

    public VideoViewDTO update(VideoInputDTO videoDto) {
        try {
            var video = repository.getById(videoDto.getId());
            video.setDescricao(videoDto.getDescricao());
            video.setTitulo(videoDto.getTitulo());
            video.setUrl(videoDto.getUrl());
            return new VideoViewDTO(repository.save(video));
        } catch (EntityNotFoundException err) {
            throw new ResourceNotFoundException("Vídeo não encontrado. Id do vídeo: " + videoDto.getId());
        }
    }

    public void delete(Long id) {
        try {
            repository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException("Video com id " + id + " não encontrado.");
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Violação de integridade da base de dados.");
        }
    }
}
