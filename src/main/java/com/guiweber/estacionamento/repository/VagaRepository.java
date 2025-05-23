package com.guiweber.estacionamento.repository;

import com.guiweber.estacionamento.entities.Vaga;
import com.guiweber.estacionamento.entities.enums.StatusVaga;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VagaRepository extends JpaRepository<Vaga, Long> {
    Optional<Vaga> findByCodigo(String cod);

    Optional<Vaga> findFirstByStatus(StatusVaga status);
}
