package br.com.fabioalvaro.sorteiocore.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import br.com.fabioalvaro.sorteiocore.mapper.SorteioMapper;
import br.com.fabioalvaro.sorteiocore.model.Cartela;
import br.com.fabioalvaro.sorteiocore.model.Linha;
import br.com.fabioalvaro.sorteiocore.model.Sorteio;
import br.com.fabioalvaro.sorteiocore.model.dto.response.CartelaNotificadosDTO;
import br.com.fabioalvaro.sorteiocore.model.dto.response.SorteioMinimoDTO;
import br.com.fabioalvaro.sorteiocore.model.dto.response.SorteioNotificadosDTO;
import br.com.fabioalvaro.sorteiocore.model.dto.response.SorteioResponseDTO;
import br.com.fabioalvaro.sorteiocore.model.enums.SorteioStatusEnum;
import br.com.fabioalvaro.sorteiocore.model.enums.TipoSorteioEnum;
import br.com.fabioalvaro.sorteiocore.model.enums.TiraTeimaEnum;
import br.com.fabioalvaro.sorteiocore.repository.CartelaRepository;
import br.com.fabioalvaro.sorteiocore.repository.SorteioRepository;

@Service
public class SorteioService {
    private static final Logger logger = LoggerFactory.getLogger(SorteioService.class);

    @Autowired
    private SorteioRepository sorteioRepository;
    @Autowired
    private CartelaRepository cartelaRepository;

    @Autowired
    private NotificacaoService notificacaoService;

    @Autowired
    private CartelaService cartelaService;

    @Autowired
    private SorteioMapper sorteioMapper;

    public Sorteio adicionarSorteio(Sorteio sorteio) {
        LocalDateTime created = LocalDateTime.now();
        sorteio.setTipoSorteio(TipoSorteioEnum.AUTOMATICO);
        sorteio.setTiraTeima(TiraTeimaEnum.AUTOMATICO);
        sorteio.setGanharamQuina(0);
        sorteio.setGanharamQuadra(0);
        sorteio.setCartelasQtd(0);
        sorteio.setCreateAt(created);
        sorteio.setUpdatedAt(LocalDateTime.now());
        sorteio.setStatus(SorteioStatusEnum.NOVO);
        sorteio.setNumeros_sorteados_qtd(0L);

        return sorteioRepository.save(sorteio);
    }

    public Optional<Sorteio> buscarSorteioPorId(String id) {
        return sorteioRepository.findById(id);
    }



    // Criado pelo quick command
    // Stackspot AI
    /**
     * Mapeia um objeto Sorteio para um objeto SorteioResponseDTO.
     *
     * @param sorteio o objeto Sorteio a ser mapeado
     * @return o objeto SorteioResponseDTO resultante do mapeamento
     */
    public SorteioResponseDTO mapeiaParaSorteioResponseDTO(@NonNull Sorteio sorteio) {

        return SorteioMapper.INSTANCE.sorteioResponseDTO(sorteio);
    }

    // Sorteia uma bola
    public int sorteiaBola(Sorteio sorteio, List<Cartela> cartelasDoSorteio) {
        logger.info("    ");
        logger.info("    ");
        logger.info("    ");
        logger.info(
                "  ***************************************************************************  ");
        logger.info("Sorteando bola para o SorteioID: {}", sorteio.getId());
        logger.info("Numeros_sorteados_qtd: {}", sorteio.getNumeros_sorteados_qtd());

        Boolean sorteioEncerrado = sorteio.getStatus().equals(SorteioStatusEnum.ENCERRADO);

        if (sorteioEncerrado == false) {
            // Preciso verificar se é a primeira bolinha e se
            // comecou a fase de QUARTAS
            if (sorteio.getNumeros_sorteados_qtd() == 0) {
                logger.info("  Primeira Bolinha  ");
                sorteio.setStatus(SorteioStatusEnum.EM_PROGRESSO_QUARTA);
                sorteioRepository.save(sorteio);
            }



            Integer numero_sorteado = sorteiaMaisUmaBolinhaNoSorteio(sorteio);


            sorteio.setUpdatedAt(LocalDateTime.now());


            // 03 ALGUEM GANHOU??
            Boolean temCartelaVencedora = false;


            List<String> ListaDeCartelasCheiasVencedoras = new ArrayList<>();


            for (Cartela cartela : cartelasDoSorteio) {
                logger.info("Cartela: {}", cartela.getId().toString());

                analisaCartela(sorteio, cartela);



                // Busca Cartelas que ganharam Full
                // quantidade de bolas sorteadas é igual ou maior que 15
                if (sorteio.getNumeros_sorteados_qtd() >= 15) {
                    logger.info(" Analise de Cartela Cheia=ON qtd={}",
                            sorteio.getNumeros_sorteados_qtd());
                    Boolean ganhouCheia = cartelaCheiaganhou(sorteio, cartela);
                    if (ganhouCheia) {
                        ListaDeCartelasCheiasVencedoras.add(cartela.getId());
                        sorteio.setGanharamFull((sorteio.getGanharamFull() + 1));
                        cartela.setGanhouCheia(true);
                        cartelaRepository.save(cartela);
                        temCartelaVencedora = true;

                    }
                }

            }
            // Verifica se Houve vencedores da Quadra... se SIM. Encerra ciclo e muda de fase.
            if (sorteio.getGanharamQuadra() > 0
                    && sorteio.getStatus() == SorteioStatusEnum.EM_PROGRESSO_QUARTA) {
                sorteio.setStatus(SorteioStatusEnum.EM_PROGRESSO_QUINA);
                sorteioRepository.save(sorteio);
                logger.info("Fechando o ciclo de Quadra", ListaDeCartelasCheiasVencedoras);
            }
            if (sorteio.getGanharamQuina() > 0
                    && sorteio.getStatus() == SorteioStatusEnum.EM_PROGRESSO_QUINA) {
                sorteio.setStatus(SorteioStatusEnum.EM_PROGRESSO_CHEIA);
                sorteioRepository.save(sorteio);
                logger.info("Fechando o ciclo de Quina", ListaDeCartelasCheiasVencedoras);
            }


            logger.info(" Cartelas Vencedoras Cheia qtd={}", ListaDeCartelasCheiasVencedoras);

            // 99 - FIM DO SORTEIO?
            if (numero_sorteado == -1 || sorteio.getNumeros_sorteados_qtd() == 75
                    || temCartelaVencedora == true) {
                logger.info("99 - FIM DO SORTEIO");
                // Finaliza e Totaliza o Sorteio encerrar o sorteio
                sorteio.setStatus(SorteioStatusEnum.ENCERRADO);

                sorteioRepository.save(sorteio);
                // @TODO NOTIFICA DONO DAS CARTELAS VENCEDORAS

                notificaVencedores(sorteio);

                return -1;

            }
            logger.info(
                    "  ***************************************************************************  ");
            logger.info("Bolas sorteadas: {}", sorteio.getLista_numeros_sorteados());
            logger.info("Numeros_sorteados_qtd: {}", sorteio.getNumeros_sorteados_qtd());
            logger.info("    ");
            logger.info("    ");
            logger.info("    ");
            return 0;

        } else {
            logger.info("99 - Sorteio ja Encerrado!!!");
            return 99;
        }

    }

    private void analisaCartela(Sorteio sorteio, Cartela cartela) {
        String FASE = "EM_PROGRESSO_QUARTA";

        if (FASE.equals(sorteio.getStatus().toString()) && cartela.getGanhouQuadra()) {
            logger.info("Cartela ja Ganhou a quadra... sera ignorada na analise.");

        } else if ("EM_PROGRESSO_QUINA".equals(sorteio.getStatus().toString())
                && cartela.getGanhouQuadra()) {
            logger.info("Cartela ja Ganhou a QUINA... sera ignorada na analise.");
        } else {

            if (sorteio.getStatus().equals(SorteioStatusEnum.EM_PROGRESSO_QUARTA)) {
                if (verificaSeLinhaGenericoGanhou(sorteio, cartela)) {
                    // se a primeira linha ja ganhou a quadra:
                    // * marca a cartela e sai...
                    cartela.setGanhouQuadra(true);
                    cartelaRepository.save(cartela);
                    sorteio.setGanharamQuadra(sorteio.getGanharamQuadra() + 1);
                    sorteioRepository.save(sorteio);
                    return;
                }


                if (verificaSeLinha2Ganhou(sorteio, cartela)) {
                    // se a segunda linha ja ganhou a quadra:
                    // * marca a cartela e sai...
                    cartela.setGanhouQuadra(true);
                    cartelaRepository.save(cartela);
                    sorteio.setGanharamQuadra(sorteio.getGanharamQuadra() + 1);
                    sorteioRepository.save(sorteio);
                    return;
                }

                if (verificaSeLinha3Ganhou(sorteio, cartela)) {
                    // se a terceira linha ja ganhou a quadra:
                    // * marca a cartela e sai...
                    cartela.setGanhouQuadra(true);
                    cartelaRepository.save(cartela);
                    sorteio.setGanharamQuadra(sorteio.getGanharamQuadra() + 1);
                    sorteioRepository.save(sorteio);
                    return;
                }
            }


            if (sorteio.getStatus().equals(SorteioStatusEnum.EM_PROGRESSO_QUINA)) {
                if (verificaSeLinhaGenericoGanhou(sorteio, cartela)) {
                    // se a primeira linha ja ganhou a quina:
                    // * marca a cartela e sai...
                    cartela.setGanhouQuina(true);
                    cartelaRepository.save(cartela);
                    sorteio.setGanharamQuina(sorteio.getGanharamQuina() + 1);
                    sorteioRepository.save(sorteio);
                    return;
                }


                if (verificaSeLinha2Ganhou(sorteio, cartela)) {
                    // se a segunda linha ja ganhou a quina:
                    // * marca a cartela e sai...
                    cartela.setGanhouQuina(true);
                    cartelaRepository.save(cartela);
                    sorteio.setGanharamQuina(sorteio.getGanharamQuina() + 1);
                    sorteioRepository.save(sorteio);
                    return;
                }

                if (verificaSeLinha3Ganhou(sorteio, cartela)) {
                    // se a terceira linha ja ganhou a quina:
                    // * marca a cartela e sai...
                    cartela.setGanhouQuina(true);
                    cartelaRepository.save(cartela);
                    sorteio.setGanharamQuina(sorteio.getGanharamQuina() + 1);
                    sorteioRepository.save(sorteio);
                    return;
                }
            }

        }


    }

    public SorteioNotificadosDTO notificaVencedores(Sorteio sorteio) {

        Optional<Sorteio> sorteioLocalizado = this.buscarSorteioPorId(sorteio.getId());
        if (sorteioLocalizado.isPresent()) {

            SorteioNotificadosDTO sorteioNotificadosDTO = new SorteioNotificadosDTO();
            sorteioNotificadosDTO.setId(sorteioLocalizado.get().getId());
            // sorteioNotificadosDTO.setLocal(sorteioLocalizado.get().getLocal());
            // sorteioNotificadosDTO.setSorteio(sorteioLocalizado.get());
            List<CartelaNotificadosDTO> listaCartelaNotificadosDTO = new ArrayList<>();

            //
            List<Cartela> listaDeCartelas =
                    cartelaService.buscarCartelasGanhouQuadraPorSorteioId(sorteio.getId());
            for (Cartela cartela : listaDeCartelas) {
                String mensagem = "Parabéns! Sua cartela " + cartela.getId() + " foi premiada!";
                notificacaoService.notificaCartela(cartela, mensagem);

                CartelaNotificadosDTO cartelaDTO = new CartelaNotificadosDTO();
                cartelaDTO.setCartelaId(cartela.getId());
                cartelaDTO.setJogadorId(cartela.getJogadorId());
                cartelaDTO.setLinha1(cartela.getLinha01());
                cartelaDTO.setLinha2(cartela.getLinha02());
                cartelaDTO.setLinha3(cartela.getLinha03());

                listaCartelaNotificadosDTO.add(cartelaDTO);
            }
            sorteioNotificadosDTO.setListaDeCartelas(listaCartelaNotificadosDTO);

            return sorteioNotificadosDTO;

        }
        return null;

    }

    public Integer sorteiaMaisUmaBolinhaNoSorteio(Sorteio sorteio) {
        // 01 - Sorteia o Numero
        Integer numero_sorteado = sorteiaNumero(sorteio);
        List<Integer> numerosSorteados = sorteio.getLista_numeros_sorteados();
        if (numerosSorteados == null) {
            numerosSorteados = new ArrayList<>();
            sorteio.setLista_numeros_sorteados(numerosSorteados);
        }
        numerosSorteados.add(numero_sorteado);
        sorteio.setNumeros_sorteados_qtd(sorteio.getNumeros_sorteados_qtd() + 1);
        sorteioRepository.save(sorteio);// adiciona bola aos numeros sorteados.

        // 02 - Falar a bolinha
        logger.info("Bola sorteada: {}", numero_sorteado);
        logger.info("Numeros Sorteados ate o momento: {}", sorteio.getNumeros_sorteados_qtd());
        if (sorteio.getNumeros_sorteados_qtd() == 75) {
            logger.info("FIM DO SORTEIO!!!", sorteio.getNumeros_sorteados_qtd() == 75);
        }
        return numero_sorteado;
    }

    private Boolean verificaSeLinha3Ganhou(Sorteio sorteio, Cartela cartela) {
        // LINHA 3

        Linha minhaLinha3 = new Linha();
        minhaLinha3.setLinha(cartela.getLinha03());
        // processa linhas QUADRA
        if (linhaganhou(sorteio, minhaLinha3, SorteioStatusEnum.EM_PROGRESSO_QUARTA, cartela)) {
            return true;
        } else {
            return false;
        }   
    }

    private Boolean verificaSeLinha2Ganhou(Sorteio sorteio, Cartela cartela) {
        // LINHA 2
        // *********************************************************************** */

        Linha minhaLinha2 = new Linha();
        minhaLinha2.setLinha(cartela.getLinha02());

        // processa linhas QUADRA
        if (linhaganhou(sorteio, minhaLinha2, SorteioStatusEnum.EM_PROGRESSO_QUARTA, cartela)) {
            return true;
        } else {
            return false;
        }
        // // processa linha QUINA
        // if (linhaganhou(sorteio, minhaLinha2, "QUINA", cartela)) {
        // sorteio.setGanharamQuina(sorteio.getGanharamQuina() + 1);
        // cartela.setGanhouQuina(true);
        // cartelaRepository.save(cartela);
        // }
        // sorteioRepository.save(sorteio);
    }

    private Boolean verificaSeLinhaGenericoGanhou(Sorteio sorteio, Cartela cartela) {
        // LINHA 1
        // *********************************************************************** */
        logger.info("   Linha1: {}", cartela.getLinha01());
        Linha minhaLinha1 = new Linha();
        minhaLinha1.setLinha(cartela.getLinha01());


        // processa linhas QUADRA
        if (linhaganhou(sorteio, minhaLinha1, sorteio.getStatus(), cartela)) {
            return true;
        } else {
            return false;
        }



    }

    private void verificaSeLinha1GanhouBKP(Sorteio sorteio, Cartela cartela) {
        // LINHA 1
        // *********************************************************************** */
        logger.info("   Linha1: {}", cartela.getLinha01());
        Linha minhaLinha1 = new Linha();
        minhaLinha1.setLinha(cartela.getLinha01());
        minhaLinha1.setGanhouQuadra(cartela.getGanhouQuadra());

        if (cartela.getGanhouQuadra().equals(null))
            minhaLinha1.setGanhouQuadra(false);
        else
            minhaLinha1.setGanhouQuadra(cartela.getGanhouQuadra());

        if (cartela.getGanhouQuina().equals(null))
            minhaLinha1.setGanhouQuina(false);
        else
            minhaLinha1.setGanhouQuina(cartela.getGanhouQuina());


        // processa linhas QUADRA
        if (linhaganhou(sorteio, minhaLinha1, SorteioStatusEnum.EM_PROGRESSO_QUARTA, cartela)) {
            sorteio.setGanharamQuadra(sorteio.getGanharamQuadra() + 1);
            cartela.setGanhouQuadra(true);
            cartelaRepository.save(cartela);
        }
        // processa linha QUINA
        if (linhaganhou(sorteio, minhaLinha1, SorteioStatusEnum.EM_PROGRESSO_QUINA, cartela)) {
            sorteio.setGanharamQuina(sorteio.getGanharamQuina() + 1);
            cartela.setGanhouQuina(true);
            cartelaRepository.save(cartela);
        }
        sorteioRepository.save(sorteio);
    }


    /**
     * Verifica se a cartela ganhou com todos os números sorteados.
     *
     * @param sorteio O objeto Sorteio contendo a lista de números sorteados.
     * @param cartela O objeto Cartela contendo os números da cartela.
     * @return true se a cartela ganhou com todos os números sorteados, false caso contrário.
     */
    private Boolean cartelaCheiaganhou(Sorteio sorteio, Cartela cartela) {
        // Log de início da análise da cartela cheia
        logger.info("");
        logger.info("       %%%%%%%%%%%%%% ANALISE CARTELA CHEIA %%%%%%%%%%%%%%%%%%%%%%");

        // Configuração da lista de números sorteados
        List<Integer> lista_sorteados = sorteio.getLista_numeros_sorteados();
        if (lista_sorteados != null) {
            Collections.sort(lista_sorteados);
        }

        // Unificação das linhas da cartela em uma única lista
        List<Integer> listaUnificada = new ArrayList<>();
        listaUnificada.addAll(cartela.getLinha01());
        listaUnificada.addAll(cartela.getLinha02());
        listaUnificada.addAll(cartela.getLinha03());

        // Contagem de números sorteados presentes na cartela
        int count = 0;
        for (Integer numero : listaUnificada) {
            if (lista_sorteados.contains(numero)) {
                count++;
            }
        }

        // Verificação se a cartela ganhou cheia
        Boolean retorno = null;
        if (count >= 15 && cartela.getGanhouCheia() == false) {
            // System.out.println(" Cartela Ganhou Cheia: " + count);
            logger.warn("          Cartela Ganhou Cheia: " + count);
            cartela.setGanhouCheia(true);
            cartelaRepository.save(cartela);
            retorno = true;
        } else {
            // Exibição do resultado
            if (cartela.getGanhouCheia()) {
                logger.warn("      Cartela já ganhou cheia.", true);
            }
            logger.info("       Numeros ja sorteados: " + count);
            retorno = false;
        }

        return retorno;
    }

    // Modo Analise QUADRA, QUINTA
    public Boolean linhaganhou(Sorteio sorteio, Linha linha, SorteioStatusEnum modoAnalise,
            Cartela cartela) {
        logger.info("");
        // logger.info(" %%%%%%%%%%%%%% LINHA ANALISE %%%%%%%%%%%%%%%%%%%%%%");
        List<SorteioStatusEnum> modosValidos = Arrays.asList(SorteioStatusEnum.EM_PROGRESSO_QUARTA,
                SorteioStatusEnum.EM_PROGRESSO_QUINA);
        if (!modosValidos.contains(modoAnalise)) {
            throw new RuntimeException("OPS!!! MODO INVALIDO DE ANALISE DE LINHA ");
        }
        // Configurando
        List<Integer> lista_sorteados = sorteio.getLista_numeros_sorteados();

        // Conjunto de números inteiros chamado linha2

        // Calcula a quantidade de números da linha2 que estão presentes nos
        // numeros_sorteados
        if (lista_sorteados != null) {
            Collections.sort(lista_sorteados);
        }

        // logger.info(" Sorteados : {}", lista_sorteados);
        int count = 0;
        for (Integer numero : linha.getLinha()) {
            if (lista_sorteados.contains(numero)) {
                count++;
            }
        }
        Boolean retorno = null;
        // SE QUADRA
        if (modoAnalise == SorteioStatusEnum.EM_PROGRESSO_QUARTA) {
            if (count == 4 && linha.isGanhouQuadra() == false
                    && cartela.getGanhouQuadra() == false) {
                System.out.println("        Linha Vencedora QUADRA: " + count);
                // cartela.setGanhouQuadra(true);
                // cartelaRepository.save(cartela);
                retorno = true;
            } else {
                // Exibe o resultado
                // if (cartela.getGanhouQuadra() == true)
                // logger.warn(" Cartela Ja ganhou a QUADRA.", true);
                // logger.info(" Quantidade de números presentes nos numeros_sorteados: " + count);
                retorno = false;
            }
        }
        // SE QUINA
        else if (modoAnalise == SorteioStatusEnum.EM_PROGRESSO_QUINA) {
            if (count == 5 && linha.isGanhouQuina() == false && cartela.getGanhouQuina() == false) {
                // System.out.println(" Linha Vencedora QUINA: " + count);
                // cartela.setGanhouQuina(true);
                // cartelaRepository.save(cartela);
                retorno = true;
            } else {
                // // Exibe o resultado
                // if (cartela.getGanhouQuina() == true)
                // logger.warn(" Cartela Ja ganhou a QUINA.", true);
                // logger.info(" Quantidade de números presentes nos numeros_sorteados: " + count);
                retorno = false;
            }
        } else {
            System.out.println("ERRO DE MODO!! ");
            retorno = false;
        }
        // retorno = retorno;
        return retorno;

    }

    public Integer sorteiaNumero(Sorteio sorteioRetornado) {
        int numeroMaximo = 75;
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
            numero = random.nextInt(numeroMaximo) + 1; // Gera um número entre 1 e 75
        } while (lista_numeros_sorteados.contains(numero));

        // int numero = random.nextInt(75) + 1; // Gera um número entre 1 e 75
        return numero;
    }

    private List<Cartela> buscaCartelasDoSorteio(String sorteioId) {

        List<Cartela> listaDeCartelas = new ArrayList<>();
        listaDeCartelas = cartelaService.buscarCartelaPorSorteioId(sorteioId);

        return listaDeCartelas;
    }

    // Novo método para buscar todos os sorteios no repositório
    public List<Sorteio> buscaTodos() {
        return sorteioRepository.findAll();
    }

    public List<SorteioMinimoDTO> buscaTodosMinimosDto() {
        return sorteioRepository.findAll().stream().map(sorteioMapper::sorteioToMinimoDTO)
                .collect(Collectors.toList());
    }

    public Sorteio atualizarSorteio(String id, Sorteio sorteioAtualizado) {
        Optional<Sorteio> optionalSorteio = sorteioRepository.findById(id);
        if (optionalSorteio.isPresent()) {
            Sorteio sorteio = optionalSorteio.get();
            sorteio.setNome(sorteioAtualizado.getNome());
            sorteio.setLocal(sorteioAtualizado.getLocal());
            sorteio.setUpdatedAt(LocalDateTime.now());
            return sorteioRepository.save(sorteio);
        } else {
            return null;
        }


    }

    public void removerSorteioPorId(String id) {
        sorteioRepository.deleteById(id);
    }

}
