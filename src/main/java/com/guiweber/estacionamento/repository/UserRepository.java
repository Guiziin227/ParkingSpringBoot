package com.guiweber.estacionamento.repository;

import com.guiweber.estacionamento.entities.User;
import com.guiweber.estacionamento.entities.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    @Query("SELECT u.role FROM User u WHERE u.username like  :username")
    Role findByRole(String username);
}
