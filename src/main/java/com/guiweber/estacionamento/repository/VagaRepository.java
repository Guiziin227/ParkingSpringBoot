package com.guiweber.estacionamento.repository;

import com.guiweber.estacionamento.entities.Vaga;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VagaRepository extends JpaRepository<Vaga, Long> {
}
