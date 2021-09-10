package com.alura.challenge.raphaelf.aluraflix.repositories;

import com.alura.challenge.raphaelf.aluraflix.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
}
