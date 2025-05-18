package com.guiweber.estacionamento.service;

import com.guiweber.estacionamento.entities.Cliente;
import com.guiweber.estacionamento.entities.ClienteVaga;
import com.guiweber.estacionamento.entities.Vaga;
import com.guiweber.estacionamento.entities.enums.StatusVaga;
import com.guiweber.estacionamento.util.EstacionamentoUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class EstacionamentoService {

    private final ClienteVagaService clienteVagaService;
    private final ClienteService clienteService;
    private final VagaService vagaService;


    @Transactional
    public ClienteVaga checkIn(ClienteVaga clienteVaga) {
        Cliente cliente = clienteService.buscaPorCpf(clienteVaga.getCliente().getCpf());
        clienteVaga.setCliente(cliente);

        Vaga vaga = vagaService.buscaPorVagaLivre();
        vaga.setStatus(StatusVaga.OCUPADA);
        clienteVaga.setVaga(vaga);
        clienteVaga.setDataEntrada(LocalDateTime.now());
        clienteVaga.setRecibo(EstacionamentoUtils.gerarRecibo());

        return clienteVagaService.salvar(clienteVaga);
    }

    @Transactional
    public ClienteVaga checkOut(String recibo) {
        ClienteVaga clienteVaga = clienteVagaService.buscarPorRecibo(recibo);

        clienteVaga.setDataSaida(LocalDateTime.now());

        BigDecimal valor = EstacionamentoUtils.calcularCusto(clienteVaga.getDataEntrada(), clienteVaga.getDataSaida());
        clienteVaga.setValor(valor);

        long totalVezes = clienteVagaService.getTotalVezesEstacionamentoCompleto(clienteVaga.getCliente().getCpf());

        BigDecimal desc = EstacionamentoUtils.calcularDesconto(valor, totalVezes);
        clienteVaga.setDesconto(desc);

        clienteVaga.getVaga().setStatus(StatusVaga.LIVRE);

        return clienteVagaService.salvar(clienteVaga);
    }
}
