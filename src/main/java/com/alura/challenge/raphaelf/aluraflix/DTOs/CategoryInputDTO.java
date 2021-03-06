package com.alura.challenge.raphaelf.aluraflix.DTOs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class CategoryInputDTO {
    @NotBlank(message = "O campo é obrigatório")
    private String titulo;
    @NotBlank(message = "O campo é obrigatório")
    @Pattern(regexp = "^#([a-fA-F0-9]{6}|[a-fA-F0-9]{3})$")
    private String cor;
}
