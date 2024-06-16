package br.com.fabioalvaro.sorteiocore.controller;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.fabioalvaro.sorteiocore.dominio.Cartela;
import br.com.fabioalvaro.sorteiocore.dominio.Jogador;
import br.com.fabioalvaro.sorteiocore.dominio.Sorteio;
import br.com.fabioalvaro.sorteiocore.dominio.Vendedor;
import br.com.fabioalvaro.sorteiocore.dominio.dto.request.CartelaDTO;
import br.com.fabioalvaro.sorteiocore.dominio.dto.response.CartelaResponseDTO;
import br.com.fabioalvaro.sorteiocore.service.CartelaService;
import br.com.fabioalvaro.sorteiocore.service.JogadorService;
import br.com.fabioalvaro.sorteiocore.service.SorteioService;
import br.com.fabioalvaro.sorteiocore.service.VendedorService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/cartela")
public class CartelaController {
    private static final Logger logger = LoggerFactory.getLogger(CartelaController.class);
    @Autowired
    private CartelaService cartelaService;
    @Autowired
    private SorteioService sorteioService;
    @Autowired
    private VendedorService vendedorService;
    @Autowired
    private JogadorService jogadorService;

    @PostMapping("/teste")
    public Cartela adicionarCartela2(@RequestBody Cartela cartela) {
        return cartelaService.adicionarCartela(cartela);
    }

    @PostMapping(produces = "application/json")
    public ResponseEntity<CartelaResponseDTO> adicionarCartela(@Valid @RequestBody CartelaDTO cartelaDTO) {
        logger.error("[CartelaController: POST ]DTO: {}", cartelaDTO.toString());
        Cartela cartela = new Cartela();
        cartela.setJogadorId(cartelaDTO.getJogadorId());
        cartela.setSorteioId(cartelaDTO.getSorteioId());
        cartela.setVendedorId(cartelaDTO.getVendedorId());
        // Validar Sorteio
        Optional<Sorteio> sorteioLocalizado = sorteioService.buscarSorteioPorId(cartelaDTO.getSorteioId());
        // Validar Vendedor
        Optional<Vendedor> vendedorLocalizado = vendedorService.buscarVendedorById(cartelaDTO.getVendedorId());
        // Validar Vendedor
        Optional<Jogador> jogadorLocalizado = jogadorService.buscarJogadorById(cartelaDTO.getJogadorId());

        if (sorteioLocalizado.isEmpty()) {
            // Sorteio não encontrado, retorne uma resposta de erro
            logger.error("[CartelaController: POST ]SorteioId nao localizado: {} ", cartelaDTO.getSorteioId());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if (vendedorLocalizado.isEmpty()) {
            // Sorteio não encontrado, retorne uma resposta de erro
            logger.error("[CartelaController: POST ]vendedorId nao localizado: {} ", cartelaDTO.getVendedorId());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if (jogadorLocalizado.isEmpty()) {
            // Sorteio não encontrado, retorne uma resposta de erro
            logger.error("[CartelaController: POST ]jogadorId nao localizado: {} ", cartelaDTO.getJogadorId());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        List<List<Integer>> linhas = cartelaService.geraNumerosRandomicos();
        cartela.setLinha01(linhas.get(0));
        cartela.setLinha02(linhas.get(1));
        cartela.setLinha03(linhas.get(2));

        Cartela savedCartela = cartelaService.adicionarCartela(cartela);

        CartelaResponseDTO responseDTO = new CartelaResponseDTO();
        responseDTO.setId(savedCartela.getId());
        responseDTO.setCreatedAt(savedCartela.getCreatedAt());
        responseDTO.setJogadorId(savedCartela.getJogadorId());
        responseDTO.setSorteioId(savedCartela.getSorteioId());
        responseDTO.setVendedorId(savedCartela.getVendedorId());
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

    @GetMapping("/{id}")
    public Optional<Cartela> buscarCartelaPorId(@PathVariable String id) {
        return cartelaService.buscarCartelaPorId(id);
    }

    @PostMapping("/premiar")
    public String premiarCartela(@RequestBody Cartela cartela) {
        logger.info("cartela {}", cartela.getId());
        Optional<Cartela> cartelaRetornada = cartelaService.buscarCartelaPorId(cartela.getId());
        if (cartelaRetornada.isPresent()) {
            cartelaService.premiarCartela(cartelaRetornada.get(), cartela.getValor());
        }

        return "OK";
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleValidationException(MethodArgumentNotValidException ex) {
        // Trate a exceção aqui e retorne uma resposta de erro apropriada
        return ResponseEntity.badRequest().body("Erro de validação: " + ex.getMessage());
    }
}
