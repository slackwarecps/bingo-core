package br.com.fabioalvaro.sorteiocore.controller.v1;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.fabioalvaro.sorteiocore.mapper.SorteioMapper;
import br.com.fabioalvaro.sorteiocore.model.Cartela;
import br.com.fabioalvaro.sorteiocore.model.Sorteio;
import br.com.fabioalvaro.sorteiocore.model.Vendedor;
import br.com.fabioalvaro.sorteiocore.model.dto.request.SorteiaBolaDTO;
import br.com.fabioalvaro.sorteiocore.model.dto.request.SorteioDTO;
import br.com.fabioalvaro.sorteiocore.model.dto.response.SorteioMinimoDTO;
import br.com.fabioalvaro.sorteiocore.model.dto.response.SorteioNotificadosDTO;
import br.com.fabioalvaro.sorteiocore.model.dto.response.SorteioResponseDTO;
import br.com.fabioalvaro.sorteiocore.service.CartelaService;
import br.com.fabioalvaro.sorteiocore.service.SorteioService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("${bingo.urlPrefixo}/v1/sorteios")
public class SorteioController {
    private static final Logger logger = LoggerFactory.getLogger(SorteioController.class);
    @Autowired
    private SorteioService sorteioService;
    @Autowired
    private CartelaService cartelaService;

    @PostMapping(path = "/sorteia-bola", produces = "application/json")
    public ResponseEntity<SorteiaBolaDTO> adicionarSorteiox(@RequestBody SorteiaBolaDTO sorteioDTO) {
        List<Cartela> cartelasDoSorteio = cartelaService.buscarCartelaPorSorteioId(sorteioDTO.getSorteioId());
        Optional<Sorteio> optionalSorteio = sorteioService.buscarSorteioPorId(sorteioDTO.getSorteioId());

        if (!optionalSorteio.isPresent()) {
            return new ResponseEntity<>(sorteioDTO, HttpStatus.NOT_FOUND);
        }

        Sorteio sorteio = optionalSorteio.get();
        int retorno = sorteioService.sorteiaBola(sorteio, cartelasDoSorteio);

        switch (retorno) {
            case 99:
            case -1:
                return new ResponseEntity<>(sorteioDTO, HttpStatus.NOT_FOUND);
            case 0:
                return new ResponseEntity<>(sorteioDTO, HttpStatus.CREATED);
            default:
                return new ResponseEntity<>(sorteioDTO, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Endpoint para notificar os ganhadores de um sorteio.
     * 
     * @param sorteioDTO Objeto contendo os dados do sorteio.
     * @return ResponseEntity contendo os dados dos ganhadores notificados e o
     *         status HTTP.
     */
    @PostMapping(path = "/notifica-ganhadores", produces = "application/json")
    public ResponseEntity<SorteioNotificadosDTO> NotificaGanhadores(@RequestBody SorteiaBolaDTO sorteioDTO) {
        SorteioNotificadosDTO sorteioNotificadosDTO;

        // Busca o sorteio pelo ID fornecido no DTO
        Optional<Sorteio> sorteio = sorteioService.buscarSorteioPorId(sorteioDTO.getSorteioId());

        if (sorteio.isPresent()) {
            // Notifica os vencedores do sorteio
            sorteioService.notificaVencedores(sorteio.get());

            // Obtém os dados dos vencedores notificados
            sorteioNotificadosDTO = SorteioMapper.INSTANCE.sorteioToNotificadosDTO(sorteio.get());
            // sorteioNotificadosDTO = sorteioService.notificaVencedores(sorteio.get());

            // Retorna os dados dos vencedores notificados com status HTTP 200 (OK)
            return new ResponseEntity<>(sorteioNotificadosDTO, HttpStatus.OK);
        } else {
            // Retorna status HTTP 404 (Not Found) se o sorteio não for encontrado
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(produces = "application/json")
    public ResponseEntity<SorteioResponseDTO> criaSorteio(@Valid @RequestBody SorteioDTO sorteioDTO) {
        if (sorteioDTO.getNome() == null || sorteioDTO.getLocal() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Sorteio sorteio = new Sorteio();
        sorteio.setCreateAt(LocalDateTime.now());
        sorteio.setUpdatedAt(LocalDateTime.now());
   
        sorteio.setLocal(sorteioDTO.getLocal());
        sorteio.setNome(sorteioDTO.getNome());

        Sorteio savedSorteio = sorteioService.adicionarSorteio(sorteio);

        SorteioResponseDTO responseDTO = new SorteioResponseDTO();
        responseDTO.setId(savedSorteio.getId());
        responseDTO.setCreateAt(savedSorteio.getCreateAt());

        SorteioResponseDTO responseDTO2 = sorteioService.mapeiaParaSorteioResponseDTO(savedSorteio);

        logger.info("Sorteio Gerada: {} ", savedSorteio);

        return new ResponseEntity<>(responseDTO2, HttpStatus.CREATED);
    }

    @GetMapping
    public List<SorteioMinimoDTO> getAllSorteios() {

        return sorteioService.buscaTodosMinimosDto();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Sorteio>> getSorteioById(@PathVariable String id) {
        Optional<Sorteio> sorteio = sorteioService.buscarSorteioPorId(id);
        return ResponseEntity.ok(sorteio);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Sorteio> atualizaSorteio(@PathVariable String id, @RequestBody SorteioDTO sorteioDTO) {
        logger.info("Atualizando sorteio com id {}", id);
        if (sorteioDTO.getNome() == null || sorteioDTO.getLocal() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

       //mapeia o dto para entidade
       Sorteio sorteio = SorteioMapper.INSTANCE.dtoParaSorteio(sorteioDTO);

  

        Sorteio updatedSorteio = sorteioService.atualizarSorteio(id, sorteio);
        return ResponseEntity.ok(updatedSorteio);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSorteio(@PathVariable String id) {
        sorteioService.removerSorteioPorId(id);
        return ResponseEntity.noContent().build();
    }

    

}
