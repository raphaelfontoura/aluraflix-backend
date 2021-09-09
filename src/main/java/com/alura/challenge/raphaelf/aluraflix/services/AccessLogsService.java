package com.alura.challenge.raphaelf.aluraflix.services;

import com.alura.challenge.raphaelf.aluraflix.DTOs.AccessLogsDTO;
import com.alura.challenge.raphaelf.aluraflix.entities.AccessLogs;
import com.alura.challenge.raphaelf.aluraflix.repositories.AccessLogsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccessLogsService {

    @Autowired
    private AccessLogsRepository repository;

    public List<AccessLogsDTO> findAll() {
        List<AccessLogs> accessLogs = repository.findAll();
        return accessLogs.stream().map(AccessLogsDTO::new).collect(Collectors.toList());
    }

    public AccessLogs save(AccessLogs accessLog) {
        return repository.save(accessLog);
    }
}
