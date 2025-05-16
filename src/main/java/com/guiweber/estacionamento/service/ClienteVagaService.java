package com.guiweber.estacionamento.service;

import com.guiweber.estacionamento.entities.Cliente;
import com.guiweber.estacionamento.entities.ClienteVaga;
import com.guiweber.estacionamento.repository.ClienteVagaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ClienteVagaService {

    private final ClienteVagaRepository clienteVagaRepository;

    @Transactional
    public ClienteVaga salvar(ClienteVaga clienteVaga) {
        return clienteVagaRepository.save(clienteVaga);
    }
}
