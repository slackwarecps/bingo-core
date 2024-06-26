package br.com.fabioalvaro.sorteiocore.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.fabioalvaro.sorteiocore.model.Jogador;
import br.com.fabioalvaro.sorteiocore.repository.JogadorRepository;

@Service
public class JogadorService {

    @Autowired
    private JogadorRepository jogadorRepository;

    public List<Jogador> findAll() {
        return jogadorRepository.findAll();
    }

    public Optional<Jogador> findById(String id) {
        return jogadorRepository.findById(id);
    }

    public Optional<Jogador> buscarJogadorById(String id) {
        return jogadorRepository.findById(id);
    }

    public Jogador save(Jogador jogador) {
        jogador.setCreatedAt(LocalDateTime.now());
        return jogadorRepository.save(jogador);
    }

    public void deleteById(String id) {
        jogadorRepository.deleteById(id);
    }
}