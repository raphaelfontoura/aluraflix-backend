package com.alura.challenge.raphaelf.aluraflix.services;

import com.alura.challenge.raphaelf.aluraflix.DTOs.*;
import com.alura.challenge.raphaelf.aluraflix.entities.Category;
import com.alura.challenge.raphaelf.aluraflix.entities.Video;
import com.alura.challenge.raphaelf.aluraflix.repositories.CategoryRepository;
import com.alura.challenge.raphaelf.aluraflix.services.exceptions.DatabaseException;
import com.alura.challenge.raphaelf.aluraflix.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository repository;


    @Transactional(readOnly = true)
    public Page<CategoryViewDTO> findAll(PageRequest pageRequest) {
        Page<Category> categories = repository.findAll(pageRequest);
        if (categories.isEmpty()) throw new ResourceNotFoundException("Não existem registros na base de dados.");

        return categories.map(CategoryViewDTO::new);
    }

    @Transactional(readOnly = true)
    public Page<CategoryWithVideosViewDTO> findAllWithVideos(PageRequest pageRequest) {
        Page<Category> categories = repository.findAll(pageRequest);

        if (categories.isEmpty()) throw new ResourceNotFoundException("Não existem registros na base de dados.");

        return categories.map(CategoryWithVideosViewDTO::new);
    }

    @Transactional(readOnly = true)
    public CategoryViewDTO findById(Long id) {
        var category = repository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(String.format("Categoria com id %d não econtrada.",id)));
        return new CategoryViewDTO(category);
    }

    @Transactional
    public CategoryViewDTO save(CategoryInputDTO categoryInput) {
        Category category = new Category(categoryInput.getTitulo(), categoryInput.getCor());
        return new CategoryViewDTO(repository.save(category));
    }

    @Transactional
    public CategoryViewDTO update(CategoryUpdateDTO dto) {
        try {
            Category category = mapper(dto);
            return new CategoryViewDTO(repository.save(category));
        } catch (EntityNotFoundException err) {
            throw new ResourceNotFoundException(String.format("Categoria de id %id não econtrado.",dto.getId()));
        }
    }

    @Transactional
    public void delete(Long id) {
        try {
            repository.deleteById(id);
        } catch (EmptyResultDataAccessException err) {
            throw new ResourceNotFoundException("Categoria não encontrada.");
        } catch (DataIntegrityViolationException err) {
            throw new DatabaseException("Violação de integridade da base de dados.");
        }
    }

    @Transactional(readOnly = true)
    public Page<VideoViewDTO> getVideosByCategory(Long id, PageRequest pageRequest) {
        Category category = repository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(String.format("Categoria com id %d não encontrada.",id)));
        List<Video> videos = category.getVideos();
        if (videos.isEmpty()) throw new ResourceNotFoundException("Nao existem videos para categoria informada.");
        List<VideoViewDTO> videosDto = videos.stream().map(VideoViewDTO::new).collect(Collectors.toList());
        return new PageImpl<>(videosDto);
    }

    private Category mapper(CategoryUpdateDTO dto) {
        Category category = repository.getById(dto.getId());
        if (dto.getTitulo() != null) category.setTitulo(dto.getTitulo());
        if (dto.getCor() != null) category.setCor(dto.getCor());

        return category;
    }

}
