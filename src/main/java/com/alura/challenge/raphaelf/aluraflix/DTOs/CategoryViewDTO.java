package com.alura.challenge.raphaelf.aluraflix.DTOs;

import com.alura.challenge.raphaelf.aluraflix.entities.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CategoryViewDTO {

    private Long id;
    private String titulo;
    private String cor;

    public CategoryViewDTO(Category category) {
        this.id = category.getId();
        this.titulo = category.getTitulo();
        this.cor = category.getCor();
    }
}
