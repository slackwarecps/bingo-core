package br.com.fabioalvaro.sorteiocore.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.fabioalvaro.sorteiocore.dominio.Cartela;
import br.com.fabioalvaro.sorteiocore.repository.CartelaRepository;

@Service
public class CartelaService {
    @Autowired
    private CartelaRepository cartelaRepository;

    public Cartela adicionarCartela(Cartela cartela) {
        return cartelaRepository.save(cartela);
    }
}