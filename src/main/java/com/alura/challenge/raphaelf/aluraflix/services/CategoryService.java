package com.alura.challenge.raphaelf.aluraflix.services;

import com.alura.challenge.raphaelf.aluraflix.DTOs.CategoryInputDTO;
import com.alura.challenge.raphaelf.aluraflix.DTOs.CategoryUpdateDTO;
import com.alura.challenge.raphaelf.aluraflix.DTOs.CategoryViewDTO;
import com.alura.challenge.raphaelf.aluraflix.entities.Category;
import com.alura.challenge.raphaelf.aluraflix.repositories.CategoryRepository;
import com.alura.challenge.raphaelf.aluraflix.services.exceptions.DatabaseException;
import com.alura.challenge.raphaelf.aluraflix.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository repository;


    public List<CategoryViewDTO> findAll() {
        var categories = repository.findAll();
        if (categories.isEmpty()) throw new ResourceNotFoundException("Não existem registros na base de dados.");

        return categories.stream().map(CategoryViewDTO::new).collect(Collectors.toList());
    }

    public CategoryViewDTO findById(Long id) {
        var category = repository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(String.format("Categoria com id %d não econtrada.",id)));
        return new CategoryViewDTO(category);
    }

    @Transactional
    public CategoryViewDTO save(CategoryInputDTO categoryInput) {
        Category category = new Category(null,categoryInput.getTitulo(), categoryInput.getCor());
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
        } catch (EntityNotFoundException err) {
            throw new ResourceNotFoundException("Categoria não econtrada.");
        } catch (DataIntegrityViolationException err) {
            throw new DatabaseException("Violação de integridade da base de dados.");
        }
    }

    private Category mapper(CategoryUpdateDTO dto) {
        Category category = repository.getById(dto.getId());
        if (dto.getTitulo() != null) category.setTitulo(dto.getTitulo());
        if (dto.getCor() != null) category.setCor(dto.getCor());

        return category;
    }

}
