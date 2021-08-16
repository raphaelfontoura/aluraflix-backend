package com.alura.challenge.raphaelf.aluraflix.DTOs;

import com.alura.challenge.raphaelf.aluraflix.entities.Video;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class VideoViewDTO {
    private Long id;
    private String titulo;
    private String descricao;
    private String url;
    private Long categoriaId;

    public VideoViewDTO(Video entity) {
        this.id = entity.getId();
        this.titulo = entity.getTitulo();
        this.descricao = entity.getDescricao();
        this.url = entity.getUrl();
        this.categoriaId = entity.getCategory().getId();
    }
}
