package com.guiweber.estacionamento.service;

import com.guiweber.estacionamento.entities.ClienteVaga;
import com.guiweber.estacionamento.exception.EntityNotFoundException;
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

    @Transactional(readOnly = true)
    public ClienteVaga buscarPorRecibo(String recibo) {
        return clienteVagaRepository.findByReciboAndDataSaidaIsNull(recibo)
                .orElseThrow(() -> new EntityNotFoundException("Recibo não encontrado ou já utilizado"));
    }
}
