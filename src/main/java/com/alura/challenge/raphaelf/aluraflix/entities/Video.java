package com.alura.challenge.raphaelf.aluraflix.entities;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "tb_videos")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Builder
@Getter @Setter
public class Video {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String titulo;
    @Column(nullable = false)
    private String descricao;
    @Column(nullable = false)
    private String url;
}
