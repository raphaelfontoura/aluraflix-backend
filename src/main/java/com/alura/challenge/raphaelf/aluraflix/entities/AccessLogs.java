package com.alura.challenge.raphaelf.aluraflix.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.Duration;
import java.time.LocalDateTime;

@AllArgsConstructor
@Getter @Setter
@Entity
@Table(name = "access_logs")
@NoArgsConstructor
public class AccessLogs {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String path;
    private String remoteAddr;
    private String username;
    private LocalDateTime data;
    private Duration duracao;
}
