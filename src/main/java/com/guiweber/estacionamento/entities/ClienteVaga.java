package com.guiweber.estacionamento.entities;

import jakarta.persistence.Column;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.io.Serializable;
import java.time.LocalDateTime;

public class ClienteVaga implements Serializable {

    @CreatedDate
    @Column(name = "data_criacao")
    private LocalDateTime dataCriacao;
    @LastModifiedDate
    @Column(name = "data_modificacao")
    private LocalDateTime dataModificacao;
    @CreatedBy
    @Column(name = "criado_por")
    private String criadoPor;
    @LastModifiedBy
    @Column(name = "modificado_por")
    private String modificadoPor;

}
