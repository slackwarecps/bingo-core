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
import br.com.fabioalvaro.sorteiocore.dominio.dto.request.CartelaDTO;
import br.com.fabioalvaro.sorteiocore.dominio.dto.response.CartelaResponseDTO;
import br.com.fabioalvaro.sorteiocore.service.CartelaService;

@RestController
@RequestMapping("/cartelas")
public class CartelaController {
    private static final Logger logger = LoggerFactory.getLogger(CartelaController.class);
    @Autowired
    private CartelaService cartelaService;

    @PostMapping("/teste")
    public Cartela adicionarCartela2(@RequestBody Cartela cartela) {
        return cartelaService.adicionarCartela(cartela);
    }

    @PostMapping(produces = "application/json")
    public ResponseEntity<CartelaResponseDTO> adicionarCartela(@RequestBody CartelaDTO cartelaDTO) {
        Cartela cartela = new Cartela();
        cartela.setCriado(cartelaDTO.getCriado());
        cartela.setJogador(cartelaDTO.getJogador());

        List<List<Integer>> linhas = cartelaService.geraNumerosRandomicos();
        cartela.setLinha01(linhas.get(0));
        cartela.setLinha02(linhas.get(1));
        cartela.setLinha03(linhas.get(2));

        Cartela savedCartela = cartelaService.adicionarCartela(cartela);

        CartelaResponseDTO responseDTO = new CartelaResponseDTO();
        responseDTO.setId(savedCartela.getId());
        responseDTO.setCriado(savedCartela.getCriado());
        responseDTO.setJogador(savedCartela.getJogador());
        responseDTO.setLinha01(savedCartela.getLinha01());
        responseDTO.setLinha02(savedCartela.getLinha02());
        responseDTO.setLinha03(savedCartela.getLinha03());

        logger.info("cartela Gerada: {} ", savedCartela);

        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

    @PostMapping("/criar")
    public String criarTeste(@RequestBody Cartela cartela) {
        return cartelaService.geraNumerosRandomicos().toString();
    }
}
