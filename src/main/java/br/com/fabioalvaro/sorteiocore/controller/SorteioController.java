package br.com.fabioalvaro.sorteiocore.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.fabioalvaro.sorteiocore.dominio.Cartela;
import br.com.fabioalvaro.sorteiocore.dominio.Sorteio;
import br.com.fabioalvaro.sorteiocore.dominio.dto.request.SorteiaBolaDTO;
import br.com.fabioalvaro.sorteiocore.dominio.dto.request.SorteioDTO;
import br.com.fabioalvaro.sorteiocore.dominio.dto.response.SorteioResponseDTO;
import br.com.fabioalvaro.sorteiocore.service.CartelaService;
import br.com.fabioalvaro.sorteiocore.service.SorteioService;

@RestController
@RequestMapping("/sorteio")
public class SorteioController {
    private static final Logger logger = LoggerFactory.getLogger(SorteioController.class);
    @Autowired
    private SorteioService sorteioService;
    @Autowired
    private CartelaService cartelaService;

    @PostMapping(path = "/sorteia-bola", produces = "application/json")
    public ResponseEntity<SorteiaBolaDTO> adicionarSorteio(@RequestBody SorteiaBolaDTO sorteioDTO) {

        List<Cartela> cartelasDoSorteio = cartelaService.buscarCartelaPorSorteioId(sorteioDTO.getSorteioId());
        Sorteio sorteio = sorteioService.buscarSorteioPorId(sorteioDTO.getSorteioId()).get();

        sorteioService.sorteiaBola(sorteio, cartelasDoSorteio);
        return new ResponseEntity<>(sorteioDTO, HttpStatus.CREATED);
    }

    @PostMapping(produces = "application/json")
    public ResponseEntity<SorteioResponseDTO> sorteiaBola(@RequestBody SorteioDTO sorteioDTO) {
        Sorteio sorteio = new Sorteio();
        sorteio.setCreateAt(sorteioDTO.getCreateAt());
        sorteio.setLocal(sorteioDTO.getLocal());

        Sorteio savedSorteio = sorteioService.adicionarSorteio(sorteio);

        SorteioResponseDTO responseDTO = new SorteioResponseDTO();
        responseDTO.setId(savedSorteio.getId());
        responseDTO.setCreateAt(savedSorteio.getCreateAt());

        SorteioResponseDTO responseDTO2 = sorteioService.mapeiaParaSorteioResponseDTO(savedSorteio);

        logger.info("Sorteio Gerada: {} ", savedSorteio);

        return new ResponseEntity<>(responseDTO2, HttpStatus.CREATED);
    }

}
