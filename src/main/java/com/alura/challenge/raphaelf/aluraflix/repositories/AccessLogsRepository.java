package com.alura.challenge.raphaelf.aluraflix.repositories;

import com.alura.challenge.raphaelf.aluraflix.entities.AccessLogs;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccessLogsRepository extends JpaRepository<AccessLogs, Long> {
    List<AccessLogs> findAllByOrderByDataDesc();
}
