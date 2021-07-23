package com.alura.challenge.raphaelf.aluraflix.DTOs;

import com.alura.challenge.raphaelf.aluraflix.entities.Video;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.autoconfigure.ldap.embedded.EmbeddedLdapProperties;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class VideoViewDTO {
    private Long id;
    private String titulo;
    private String descricao;
    private String url;

    public VideoViewDTO(Video entity) {
        this.id = entity.getId();
        this.titulo = entity.getTitulo();
        this.descricao = entity.getDescricao();
        this.url = entity.getUrl();
    }
}
