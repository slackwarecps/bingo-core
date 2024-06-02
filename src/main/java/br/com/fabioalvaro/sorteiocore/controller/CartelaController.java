package br.com.fabioalvaro.sorteiocore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.fabioalvaro.sorteiocore.dominio.Cartela;
import br.com.fabioalvaro.sorteiocore.dominio.dto.request.CartelaDTO;
import br.com.fabioalvaro.sorteiocore.service.CartelaService;

@RestController
@RequestMapping("/cartelas")
public class CartelaController {
    @Autowired
    private CartelaService cartelaService;

    @PostMapping("/teste")
    public Cartela adicionarCartela2(@RequestBody Cartela cartela) {
        return cartelaService.adicionarCartela(cartela);
    }

    @PostMapping
    public CartelaDTO adicionarCartela(@RequestBody CartelaDTO cartelaDTO) {
        Cartela cartela = new Cartela();
        cartela.setCriado(cartelaDTO.getCriado());
        cartela.setJogador(cartelaDTO.getJogador());

        Cartela savedCartela = cartelaService.adicionarCartela(cartela);

        CartelaDTO responseDTO = new CartelaDTO();
        responseDTO.setId(savedCartela.getId());
        responseDTO.setCriado(savedCartela.getCriado());
        responseDTO.setJogador(savedCartela.getJogador());

        return responseDTO;
    }
}
