package com.alura.challenge.raphaelf.aluraflix.controllers;

import com.alura.challenge.raphaelf.aluraflix.DTOs.*;
import com.alura.challenge.raphaelf.aluraflix.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/categorias")
public class CategoryController {
    @Autowired
    private CategoryService service;

    @GetMapping
    public ResponseEntity<Page<CategoryViewDTO>> findAll(@RequestParam(value = "page", defaultValue = "0") Integer page) {
        PageRequest pageRequest = PageRequest.of(page,5);
        return ResponseEntity.ok(service.findAll(pageRequest));
    }

    @GetMapping(params = "_embed=videos")
    public ResponseEntity<Page<CategoryWithVideosViewDTO>> findAllWithVideos(@RequestParam(value = "page", defaultValue = "0") Integer page) {
        PageRequest pageRequest = PageRequest.of(page,5);
        return ResponseEntity.ok(service.findAllWithVideos(pageRequest));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryViewDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<CategoryViewDTO> create(@RequestBody @Valid CategoryInputDTO categoryInput, UriComponentsBuilder uriBuilder) {
        CategoryViewDTO categoryViewDto = service.save(categoryInput);
        URI uri = uriBuilder.path("/categorias/{id}").buildAndExpand(categoryViewDto.getId()).toUri();
        return ResponseEntity.created(uri).body(categoryViewDto);
    }

    @PutMapping
    public ResponseEntity<CategoryViewDTO> update(@RequestBody CategoryUpdateDTO dto) {
        CategoryViewDTO categoryViewDto = service.update(dto);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(categoryViewDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/{id}/videos")
    public ResponseEntity<Page<VideoViewDTO>> getVideosByCategory(@PathVariable Long id) {
        PageRequest pageRequest = PageRequest.of(0,5);
        return ResponseEntity.ok(service.getVideosByCategory(id, pageRequest));
    }
}
