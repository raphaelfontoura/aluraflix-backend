package com.alura.challenge.raphaelf.aluraflix.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class Video {
    private Long id;
    private String titulo;
    private String descricao;
    private String url;
}
