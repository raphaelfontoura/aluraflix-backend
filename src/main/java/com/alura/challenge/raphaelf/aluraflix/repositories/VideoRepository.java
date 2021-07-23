package com.alura.challenge.raphaelf.aluraflix.repositories;

import com.alura.challenge.raphaelf.aluraflix.entities.Video;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VideoRepository extends JpaRepository<Video, Long> {
}
