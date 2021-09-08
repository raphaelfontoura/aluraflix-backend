package com.alura.challenge.raphaelf.aluraflix.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@AllArgsConstructor
@Getter @Setter
@Entity
@Table(name = "authorities")
public class Authority {
    @Id
    @Enumerated(EnumType.STRING)
    private Role authority;
    private String username;

    public enum Role {
        ADMIN, USER
    }

}
