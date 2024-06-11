package br.com.fabioalvaro.sorteiocore.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.fabioalvaro.sorteiocore.dominio.Cartela;
import br.com.fabioalvaro.sorteiocore.dominio.Linha;
import br.com.fabioalvaro.sorteiocore.dominio.Sorteio;
import br.com.fabioalvaro.sorteiocore.dominio.dto.response.SorteioResponseDTO;
import br.com.fabioalvaro.sorteiocore.dominio.enums.TipoSorteioEnum;
import br.com.fabioalvaro.sorteiocore.dominio.enums.TiraTeimaEnum;
import br.com.fabioalvaro.sorteiocore.repository.SorteioRepository;

@Service
public class SorteioService {
    private static final Logger logger = LoggerFactory.getLogger(SorteioService.class);

    private ModelMapper modelMapperSorteio = new ModelMapper();

    @Autowired
    private SorteioRepository sorteioRepository;

    @Autowired
    private CartelaService cartelaService;

    public Sorteio adicionarSorteio(Sorteio sorteio) {
        LocalDateTime created = LocalDateTime.now();
        sorteio.setTipoSorteio(TipoSorteioEnum.AUTOMATICO);
        sorteio.setTiraTeima(TiraTeimaEnum.AUTOMATICO);
        sorteio.setGanharamQuina(0);
        sorteio.setGanharamQuadra(0);
        sorteio.setCartelasQtd(0);
        sorteio.setCreateAt(created);
        sorteio.setStatus("ATIVO");
        sorteio.setNumeros_sorteados_qtd(0L);
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
        logger.info("    ");
        logger.info("    ");
        logger.info("    ");
        logger.info("  ***************************************************************************  ");
        logger.info("Sorteando bola para o SorteioID: {}", sorteioId);
        Optional<Sorteio> sorteio = sorteioRepository.findById(sorteioId);
        if (sorteio.isPresent()) {
            Sorteio sorteioRetornado = sorteio.get();

            Boolean sorteioEncerrado = (sorteio.get().getStatus() == "ENCERRADO");

            if (sorteioEncerrado != true) {
                // 01 - Sorteia o Numero
                Integer numero_sorteado = sorteiaNumero(sorteioRetornado);

                // 02 - Falar a bolinha
                logger.info("Bola sorteada: {}", numero_sorteado);

                // 03 ALGUEM GANHOU??
                // Boolean alguemGanhou = cartelasGanhadorasComEssaBola(sorteioRetornado);

                // sorteioId = "6667a5f4166fd20b978148a6";
                List<Cartela> cartelasDoSorteio = buscaCartelasDoSorteio(sorteioId);
                for (Cartela cartela : cartelasDoSorteio) {
                    logger.info("Cartela: {}", cartela.getId().toString());
                    // processa linhas QUADRA
                    logger.info("   Linha1: {}", cartela.getLinha01());
                    Linha minhaLinha = new Linha();
                    minhaLinha.setLinha(cartela.getLinha01());
                    minhaLinha.setGanhouQuadra(false);
                    minhaLinha.setGanhouQuina(false);
                    logger.info(minhaLinha.toString());

                    linhaganhou(sorteioRetornado, minhaLinha, "QUADRA");

                    logger.info("   Linha2: {}", cartela.getLinha02());
                    logger.info("   Linha3: {}", cartela.getLinha03());
                }

                // 99 - FIM DO SORTEIO?
                // numero_sorteado = -1;
                if (numero_sorteado == -1) {
                    logger.info("99 - FIM DO SORTEIO");

                    // XX - Finaliza e Totaliza o Sorteio encerrar o sorteio
                    sorteioRetornado.setStatus("ENCERRADO");
                    sorteioRepository.save(sorteioRetornado);

                    // XXX - NOTIFICA DONO DAS CARTELAS VENCEDORAS
                    // @TODO NOTIFICA DONO DAS CARTELAS VENCEDORAS

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

    // Modo Analise QUADRA, QUINTA
    public Boolean linhaganhou(Sorteio sorteio, Linha linha, String modoAnalise) {
        List<String> modosValidos = Arrays.asList("QUADRA", "QUINA");
        if (!modosValidos.contains(modoAnalise)) {
            throw new RuntimeException("OPS!!! MODO INVALIDO DE ANALISE DE LINHA ");
        }
        // Configurando
        List<Integer> lista_sorteados = sorteio.getLista_numeros_sorteados();

        // Conjunto de números inteiros chamado linha2

        // Calcula a quantidade de números da linha2 que estão presentes nos
        // numeros_sorteados
        logger.info("Linha : {}", linha.getLinha());
        logger.info(">> Linha : {}", linha.isGanhouQuadra());
        logger.info(">> Linha : {}", linha.isGanhouQuina());
        logger.info("Sorteados : {}", lista_sorteados);
        int count = 0;
        for (Integer numero : linha.getLinha()) {
            if (lista_sorteados.contains(numero)) {
                count++;
            }
        }
        Boolean retorno = null;
        // SE QUADRA
        if (modoAnalise == "QUADRA") {
            if (count >= 4 && linha.isGanhouQuadra() == false) {
                System.out.println("Linha Vencedora QUADRA: " + count);

                retorno = true;
            } else {
                // Exibe o resultado
                System.out.println("Quantidade de números presentes nos numeros_sorteados: " + count);
                retorno = false;
            }
        }
        // SE QUINA
        else if (modoAnalise == "QUINA") {
            if (count >= 5 && linha.isGanhouQuina() == false) {
                System.out.println("Linha Vencedora QUINA: " + count);
                retorno = true;
            } else {
                // Exibe o resultado
                System.out.println("Quantidade de números presentes nos numeros_sorteados: " + count);
                retorno = false;
            }
        } else {
            System.out.println("ERRO DE MODO!! ");
            retorno = false;
        }
        // retorno = retorno;
        return retorno;

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

    private List<Cartela> buscaCartelasDoSorteio(String sorteioId) {

        List<Cartela> listaDeCartelas = new ArrayList<>();
        listaDeCartelas = cartelaService.buscarCartelaPorSorteioId(sorteioId);

        return listaDeCartelas;
    }

}