package com.alura.challenge.raphaelf.aluraflix.DTOs;

import com.alura.challenge.raphaelf.aluraflix.entities.Category;
import com.alura.challenge.raphaelf.aluraflix.entities.Video;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Getter
public class CategoryWithVideosViewDTO {

    private Long id;
    private String titulo;
    private String cor;
    private List<VideoViewDTO> videos = new ArrayList<>();

    public CategoryWithVideosViewDTO(Category category) {
        this.id = category.getId();
        this.titulo = category.getTitulo();
        this.cor = category.getCor();
        List<Video> videos = category.getVideos();
        videos.forEach(video -> this.videos.add(new VideoViewDTO(video)));
    }

}
