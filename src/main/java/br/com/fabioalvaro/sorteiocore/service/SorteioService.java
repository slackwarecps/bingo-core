package br.com.fabioalvaro.sorteiocore.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.fabioalvaro.sorteiocore.dominio.Sorteio;
import br.com.fabioalvaro.sorteiocore.repository.SorteioRepository;

@Service
public class SorteioService {

    @Autowired
    private SorteioRepository sorteioRepository;

    public Sorteio adicionarSorteio(Sorteio sorteio) {
        return sorteioRepository.save(sorteio);
    }

}