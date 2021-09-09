package com.alura.challenge.raphaelf.aluraflix.repositories;

import com.alura.challenge.raphaelf.aluraflix.entities.AccessLogs;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccessLogsRepository extends JpaRepository<AccessLogs, Long> {
}
