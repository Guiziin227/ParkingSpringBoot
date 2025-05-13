package com.guiweber.estacionamento.repository;

import com.guiweber.estacionamento.entities.Cliente;
import com.guiweber.estacionamento.repository.projection.ClienteProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    @Query("SELECT c FROM Cliente c")
    Page<ClienteProjection> findAllPageable(Pageable pageable);
}
