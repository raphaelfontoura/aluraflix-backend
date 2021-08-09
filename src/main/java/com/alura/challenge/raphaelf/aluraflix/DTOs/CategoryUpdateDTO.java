package com.alura.challenge.raphaelf.aluraflix.DTOs;

import com.alura.challenge.raphaelf.aluraflix.entities.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@AllArgsConstructor
@Getter @Setter
public class CategoryUpdateDTO {
    @NotNull
    private Long id;
    private String titulo;
    @Pattern(regexp = "^#([a-fA-F0-9]{6}|[a-fA-F0-9]{3})$")
    private String cor;

}
