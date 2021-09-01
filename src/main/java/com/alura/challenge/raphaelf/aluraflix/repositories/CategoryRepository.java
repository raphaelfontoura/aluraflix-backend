package com.alura.challenge.raphaelf.aluraflix.repositories;

import com.alura.challenge.raphaelf.aluraflix.entities.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    @Query(value = "SELECT DISTINCT c FROM Category c JOIN c.videos v")
    Page<Category> findAllJoinVideos(Pageable page);
}
