package br.com.fabioalvaro.sorteiocore.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.fabioalvaro.sorteiocore.dominio.Sorteio;
import br.com.fabioalvaro.sorteiocore.dominio.dto.response.SorteioResponseDTO;
import br.com.fabioalvaro.sorteiocore.repository.SorteioRepository;

@Service
public class SorteioService {
    private static final Logger logger = LoggerFactory.getLogger(SorteioService.class);

    private ModelMapper modelMapperSorteio = new ModelMapper();

    @Autowired
    private SorteioRepository sorteioRepository;

    public Sorteio adicionarSorteio(Sorteio sorteio) {
        LocalDateTime created = LocalDateTime.now();
        sorteio.setCreateAt(created);
        return sorteioRepository.save(sorteio);
    }

    public Optional<Sorteio> buscarSorteioPorId(String id) {
        return sorteioRepository.findById(id);
    }

    // Sorteio >> SorteioResponseDTO
    public SorteioResponseDTO mapeiaParaSorteioResponseDTO(Sorteio sorteio) {
        return modelMapperSorteio.map(sorteio, SorteioResponseDTO.class);
    }

    // Sorteia uma bola
    public void sorteiaBola(String sorteioId) {

        logger.info("Sorteando bola para o SorteioID: {}", sorteioId);
        Optional<Sorteio> sorteio = sorteioRepository.findById(sorteioId);
        if (sorteio.isPresent()) {
            Sorteio sorteioRetornado = sorteio.get();
            Boolean sorteioEncerrado = true;

            if (sorteioEncerrado == true) {
                // 01 - Sorteia o Numero
                Integer numero_sorteado = sorteiaNumero(sorteioRetornado);

                // 01 - Falar a bolinha
                logger.info("Bola sorteada: {}", numero_sorteado);

                // 99 - FIM DO SORTEIO?
                numero_sorteado = -1;
                if (numero_sorteado == -1) {
                    logger.info("99 - FIM DO SORTEIO");
                    // encerrar sorteio

                    return;

                    // throw new RuntimeException("<<< TODAS AS BOLAS JA FORAM SORTEADAS >>>");

                }

                // Insere bola na lista de nunmeros ja sorteados.
                List<Integer> lista_atual = sorteioRetornado.getLista_numeros_sorteados();
                // Inicializa a lista se estiver nula
                if (lista_atual == null) {
                    lista_atual = new ArrayList<>();
                    // sorteioRetornado.setLista_numeros_sorteados(lista_atual);
                }

                lista_atual.add(numero_sorteado);
                sorteioRetornado.setLista_numeros_sorteados(lista_atual);
                sorteioRepository.save(sorteioRetornado);

                logger.info("AtualizadoLista de bolas sorteadas: {}", lista_atual);
            } else {
                logger.info("99 - Sorteio ja Encerrado!!!");
            }

        } else {
            throw new RuntimeException("Sorteio não existe!!");
        }

        // TODO Auto-generated method stub

    }

    private Integer sorteiaNumero(Sorteio sorteioRetornado) {
        Random random = new Random();
        // Lista dos numeros ja sorteados
        List<Integer> lista_numeros_sorteados = sorteioRetornado.getLista_numeros_sorteados();

        // Inicializa a lista se estiver nula
        if (lista_numeros_sorteados == null) {
            lista_numeros_sorteados = new ArrayList<>();
            sorteioRetornado.setLista_numeros_sorteados(lista_numeros_sorteados);
        }

        // Verifica se todos os números já foram sorteados
        if (lista_numeros_sorteados.size() >= 75) { // Supondo que o intervalo é de 1 a 75
            return -1;
        }

        int numero;
        do {
            numero = random.nextInt(10) + 1; // Gera um número entre 1 e 75
        } while (lista_numeros_sorteados.contains(numero));

        // int numero = random.nextInt(75) + 1; // Gera um número entre 1 e 75
        return numero;
    }

}