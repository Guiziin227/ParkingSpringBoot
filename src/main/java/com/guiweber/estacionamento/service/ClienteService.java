package com.guiweber.estacionamento.service;

import com.guiweber.estacionamento.entities.Cliente;
import com.guiweber.estacionamento.exception.CpfUniqueViolationException;
import com.guiweber.estacionamento.exception.UserNotFoundException;
import com.guiweber.estacionamento.repository.ClienteRepository;
import com.guiweber.estacionamento.repository.projection.ClienteProjection;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClienteService {

    private final ClienteRepository clienteRepository;

    @Transactional
    public Cliente salvar(Cliente cliente) {
        try{
            return clienteRepository.save(cliente);
        }catch (DataIntegrityViolationException e){
            throw new CpfUniqueViolationException("Cpf ja cadastrado " + e.getMessage());
        }
    }

    @Transactional(readOnly = true)
    public Cliente findById(Long id) {
        return clienteRepository.findById(id).orElseThrow(() -> new UserNotFoundException("Cliente não encontrado"));
    }

    @Transactional(readOnly = true)
    public Page<ClienteProjection> findAll(Pageable pageable) {
        return clienteRepository.findAllPageable(pageable);
    }

}
