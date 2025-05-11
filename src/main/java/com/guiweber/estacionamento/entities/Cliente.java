package com.guiweber.estacionamento.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Getter
@Table(name = "clientes")
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Setter
@EntityListeners(AuditingEntityListener.class)
public class Cliente implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome", nullable = false, length = 100)
    private String nome;

    @Column(name = "cpf", nullable = false, unique = true, length = 11)
    private String cpf;

    @OneToOne //dizendo relacionamento
    @JoinColumn(name = "id_usuario", nullable = false)//Fazendo mapeamento entre as duas classes
    private Usuario usuario;

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

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Cliente cliente = (Cliente) o;
        return Objects.equals(id, cliente.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
