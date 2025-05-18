package com.guiweber.estacionamento.service;

import com.guiweber.estacionamento.entities.ClienteVaga;
import com.guiweber.estacionamento.exception.EntityNotFoundException;
import com.guiweber.estacionamento.repository.ClienteRepository;
import com.guiweber.estacionamento.repository.ClienteVagaRepository;
import com.guiweber.estacionamento.repository.projection.ClienteVagaProjection;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ClienteVagaService {

    private final ClienteVagaRepository clienteVagaRepository;
    private final ClienteRepository clienteRepository;

    @Transactional
    public ClienteVaga salvar(ClienteVaga clienteVaga) {
        return clienteVagaRepository.save(clienteVaga);
    }

    @Transactional(readOnly = true)
    public ClienteVaga buscarPorRecibo(String recibo) {
        return clienteVagaRepository.findByReciboAndDataSaidaIsNull(recibo)
                .orElseThrow(() -> new EntityNotFoundException("Recibo não encontrado ou já utilizado"));
    }

    @Transactional(readOnly = true)
    public long getTotalVezesEstacionamentoCompleto(String cpf) {
        return clienteVagaRepository.countByClienteCpfAndDataSaidaIsNotNull(cpf);
    }

    @Transactional
    public Page<ClienteVagaProjection> buscarTodosPorClienteCpf(String cpf, Pageable pageable) {
        return clienteVagaRepository.findAllByClienteCpf(cpf, pageable);

    }
}
