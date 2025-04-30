package com.guiweber.estacionamento.repository;

import com.guiweber.estacionamento.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
}
