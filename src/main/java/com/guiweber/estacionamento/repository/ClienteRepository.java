package com.guiweber.estacionamento.repository;

import com.guiweber.estacionamento.entities.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
}
