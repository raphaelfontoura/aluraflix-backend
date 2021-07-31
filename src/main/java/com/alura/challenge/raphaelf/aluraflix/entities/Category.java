package com.alura.challenge.raphaelf.aluraflix.entities;

import com.alura.challenge.raphaelf.aluraflix.DTOs.VideoViewDTO;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tb_categories")
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@EqualsAndHashCode(of = "id")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;
    @Column(nullable = false)
    private String titulo;
    @Column(nullable = false)
    private String cor;
    @OneToMany(mappedBy = "category")
    @Setter(AccessLevel.NONE)
    private List<Video> videos = new ArrayList<>();
}
