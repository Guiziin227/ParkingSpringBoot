package com.guiweber.estacionamento.repository;

import com.guiweber.estacionamento.entities.Usuario;
import com.guiweber.estacionamento.entities.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByUsername(String username);

    @Query("SELECT u.role FROM Usuario u WHERE u.username like  :username")
    Role findByRole(String username);
}
