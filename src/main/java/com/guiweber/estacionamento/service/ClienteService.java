package com.guiweber.estacionamento.service;

import com.guiweber.estacionamento.entities.Cliente;
import com.guiweber.estacionamento.exception.CpfUniqueViolationException;
import com.guiweber.estacionamento.exception.UserNotFoundException;
import com.guiweber.estacionamento.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

}
