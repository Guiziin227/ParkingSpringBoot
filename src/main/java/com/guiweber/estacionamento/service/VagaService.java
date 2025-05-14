package com.guiweber.estacionamento.service;

import com.guiweber.estacionamento.entities.Vaga;
import com.guiweber.estacionamento.exception.CodUniqueViolationException;
import com.guiweber.estacionamento.exception.EntityNotFoundException;
import com.guiweber.estacionamento.repository.VagaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class VagaService {

    private final VagaRepository vagaRepository;

    @Transactional
    public Vaga save(Vaga vaga) {
        try{
            return vagaRepository.save(vaga);
        }
        catch (DataIntegrityViolationException e){
            throw new CodUniqueViolationException("Codigo ja cadastrado " + e.getMessage());
        }
    }

    @Transactional(readOnly = true)
    public Vaga findByCod(String cod) {
        return vagaRepository.findByCod(cod).orElseThrow(
                () -> new EntityNotFoundException("Vaga não encontrada com o código: " + cod)
        );
    }

}
