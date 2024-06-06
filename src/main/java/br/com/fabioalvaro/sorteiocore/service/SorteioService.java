package br.com.fabioalvaro.sorteiocore.service;

import java.time.LocalDateTime;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.fabioalvaro.sorteiocore.dominio.Sorteio;
import br.com.fabioalvaro.sorteiocore.dominio.dto.response.SorteioResponseDTO;
import br.com.fabioalvaro.sorteiocore.repository.SorteioRepository;

@Service
public class SorteioService {
    private ModelMapper modelMapperSorteio = new ModelMapper();

    @Autowired
    private SorteioRepository sorteioRepository;

    public Sorteio adicionarSorteio(Sorteio sorteio) {
        LocalDateTime created = LocalDateTime.now();
        sorteio.setCreateAt(created);
        return sorteioRepository.save(sorteio);
    }

    public SorteioResponseDTO mapeiaParaSorteioResponseDTO(Sorteio sorteio) {
        return modelMapperSorteio.map(sorteio, SorteioResponseDTO.class);
    }

}