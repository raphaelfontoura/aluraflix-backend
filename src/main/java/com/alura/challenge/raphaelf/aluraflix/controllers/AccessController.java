package com.alura.challenge.raphaelf.aluraflix.controllers;

import com.alura.challenge.raphaelf.aluraflix.DTOs.AccessLogsDTO;
import com.alura.challenge.raphaelf.aluraflix.entities.AccessLogs;
import com.alura.challenge.raphaelf.aluraflix.repositories.AccessLogsRepository;
import com.alura.challenge.raphaelf.aluraflix.services.AccessLogsService;
import com.alura.challenge.raphaelf.aluraflix.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/log-acessos")
public class AccessController {

    @Autowired
    private AccessLogsService service;

    @GetMapping
    public List<AccessLogsDTO> getAcessos() {
        List<AccessLogsDTO> accessLogs = service.findAll();
        if (accessLogs.isEmpty()) {
            throw new ResourceNotFoundException("nenhum log encontrado na base de dados.");
        }
        return accessLogs;
    }
}
