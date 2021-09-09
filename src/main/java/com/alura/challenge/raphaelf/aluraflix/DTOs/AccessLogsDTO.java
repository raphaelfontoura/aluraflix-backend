package com.alura.challenge.raphaelf.aluraflix.DTOs;

import com.alura.challenge.raphaelf.aluraflix.entities.AccessLogs;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Duration;
import java.time.LocalDateTime;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccessLogsDTO {

    private Long id;
    private String path;
    private String remoteAddr;
    private String username;
    private LocalDateTime data;
    private Duration duracao;

    public AccessLogsDTO(AccessLogs entity) {
        id = entity.getId();
        path = entity.getPath();
        remoteAddr = entity.getRemoteAddr();
        username = entity.getUsername();
        data = entity.getData();
        duracao = entity.getDuracao();
    }

}
