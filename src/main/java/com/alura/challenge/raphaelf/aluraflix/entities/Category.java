package com.alura.challenge.raphaelf.aluraflix.entities;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "tb_categories")
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@ToString
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
    @ToString.Exclude
    private List<Video> videos = new ArrayList<>();

    public Category(String titulo, String cor) {
        this.titulo = titulo;
        this.cor = cor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Category category = (Category) o;
        return id.equals(category.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
