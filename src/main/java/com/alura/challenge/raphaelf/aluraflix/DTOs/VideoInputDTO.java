package com.alura.challenge.raphaelf.aluraflix.DTOs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@AllArgsConstructor
@Getter @Setter
public class VideoInputDTO {
    private Long id;
    @Size(min = 10, message = "Tamanho mínimo de 10 caracteres")
    @NotBlank(message = "Campo requerido")
    private String titulo;
    @NotBlank(message = "Campo requerido")
    private String descricao;
    @NotBlank(message = "Campo requerido")
    @URL(message = "Informe uma URL válida.")
    private String url;
}
