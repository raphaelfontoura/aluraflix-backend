package com.alura.challenge.raphaelf.aluraflix.DTOs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@AllArgsConstructor
@Getter @Setter
public class CategoryInputDTO {
    private Long id;
    @NotBlank(message = "O campo é obrigatório")
    private String titulo;
    @NotBlank(message = "O campo é obrigatório")
    @Pattern(regexp = "^#([a-fA-F0-9]{6}|[a-fA-F0-9]{3})$")
    private String cor;
}
