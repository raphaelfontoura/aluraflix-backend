package com.alura.challenge.raphaelf.aluraflix.controllers;

import com.alura.challenge.raphaelf.aluraflix.DTOs.VideoInputDTO;
import com.alura.challenge.raphaelf.aluraflix.DTOs.VideoUpdateDTO;
import com.alura.challenge.raphaelf.aluraflix.DTOs.VideoViewDTO;
import com.alura.challenge.raphaelf.aluraflix.services.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.querydsl.QPageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/videos")
public class VideoController {
    @Autowired
    private VideoService service;

    @GetMapping
    public ResponseEntity<Page<VideoViewDTO>> findAll(@RequestParam(value = "page", defaultValue = "0") Integer page, String search) {
        PageRequest pageRequest = PageRequest.of(page, 5);
        if (search != null) return ResponseEntity.ok(service.findByTitulo(search, pageRequest));
        return ResponseEntity.ok(service.findAll(pageRequest));
    }


    @GetMapping(value = "/{id}")
    public ResponseEntity<VideoViewDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<VideoViewDTO> save(@RequestBody @Valid VideoInputDTO videoDto, UriComponentsBuilder uriBuilder) {
        var videoViewDto = service.save(videoDto);
        URI uri = uriBuilder.path("/videos/{id}").buildAndExpand(videoViewDto.getId()).toUri();
        return ResponseEntity.created(uri).body(videoViewDto);
    }

    @PutMapping
    public ResponseEntity<VideoViewDTO> update(@RequestBody @Valid VideoUpdateDTO dto) {
        return ResponseEntity.accepted().body(service.update(dto));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
