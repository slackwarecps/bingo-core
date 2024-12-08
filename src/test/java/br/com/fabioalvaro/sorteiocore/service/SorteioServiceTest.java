package br.com.fabioalvaro.sorteiocore.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import br.com.fabioalvaro.sorteiocore.model.Cartela;
import br.com.fabioalvaro.sorteiocore.model.Linha;
import br.com.fabioalvaro.sorteiocore.model.Sorteio;
import br.com.fabioalvaro.sorteiocore.model.enums.SorteioStatusEnum;
import br.com.fabioalvaro.sorteiocore.repository.CartelaRepository;
import br.com.fabioalvaro.sorteiocore.repository.SorteioRepository;

public class SorteioServiceTest {
        @Mock
    private SorteioRepository sorteioRepository;

    @Mock
    private CartelaRepository cartelaRepository;



    @InjectMocks
    private SorteioService sorteioService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    public void naoDeveriaGanharQuadraComTresNumeros() {
        // Configuração do sorteio com os números sorteados
        Sorteio sorteio = new Sorteio();
        sorteio.setLista_numeros_sorteados(
                Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15));

        // Configuração da linha com apenas três números
        Linha linha = new Linha();
        linha.setLinha(Arrays.asList(1, 2, 3));
        linha.setGanhouQuadra(false); // Linha ainda não ganhou a quadra
        linha.setGanhouQuina(false); // Linha ainda não ganhou a quina

        // Configuração da cartela
        Cartela cartela = new Cartela();
        cartela.setGanhouQuadra(false); // Cartela ainda não ganhou a quadra
        cartela.setGanhouQuina(false); // Cartela ainda não ganhou a quina


        // Execução do método com o modo de análise "QUADRA"
        Boolean result = sorteioService.linhaganhou(sorteio, linha, SorteioStatusEnum.EM_PROGRESSO_QUARTA, cartela);

        // Validação: o resultado deve ser false, pois a linha tem apenas 3 números
        assertFalse(result, "A linha não deveria ganhar a quadra com apenas 3 números.");
    }

        @Test
    public void deveriaRetornarTrueQuandoLinhaContiverPeloMenosQuatroNumeros() {
        // Configuração do sorteio com os números sorteados
        Sorteio sorteio = new Sorteio();
        sorteio.setLista_numeros_sorteados(
                Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15));

        // Configuração da linha com pelo menos quatro números
        Linha linha = new Linha();
        linha.setLinha(Arrays.asList(1, 2, 3, 15));
        linha.setGanhouQuadra(false); // Linha ainda não ganhou a quadra
        linha.setGanhouQuina(false); // Linha ainda não ganhou a quina

        // Configuração da cartela
        Cartela cartela = new Cartela();
        cartela.setGanhouQuadra(false); // Cartela ainda não ganhou a quadra
        cartela.setGanhouQuina(false); // Cartela ainda não ganhou a quina

        // Execução do método com o modo de análise "QUADRA"
        Boolean result = sorteioService.linhaganhou(sorteio, linha, SorteioStatusEnum.EM_PROGRESSO_QUARTA, cartela);

        // Validação: o resultado deve ser true, pois a linha contém pelo menos 4 números
        assertTrue(result, "A linha deveria ganhar quando contiver pelo menos 4 números.");
    }


    @Test
    public void deveriaRetornarTrueQuandoLinhaContiverPeloMenosCincoNumerosNoModoQuina() {
        // Configuração do sorteio com os números sorteados
        Sorteio sorteio = new Sorteio();
        sorteio.setLista_numeros_sorteados(
                Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15));

        // Configuração da linha com pelo menos cinco números
        Linha linha = new Linha();
        linha.setLinha(Arrays.asList(1, 2, 3, 4, 5));
        linha.setGanhouQuadra(false); // Linha ainda não ganhou a quadra
        linha.setGanhouQuina(false); // Linha ainda não ganhou a quina

        // Configuração da cartela
        Cartela cartela = new Cartela();
        cartela.setGanhouQuadra(false); // Cartela ainda não ganhou a quadra
        cartela.setGanhouQuina(false); // Cartela ainda não ganhou a quina

        // Execução do método com o modo de análise "QUINA"
        Boolean result = sorteioService.linhaganhou(sorteio, linha, SorteioStatusEnum.EM_PROGRESSO_QUINA, cartela);

        // Validação: o resultado deve ser true, pois a linha contém pelo menos 5 números
        assertTrue(result, "A linha deveria ganhar a quina com pelo menos 5 números.");
    }


    @Test
    public void deveriaFalharAoSortearBolaParaSorteioEncerrado() {
        // Configuração do sorteio com status ENCERRADO
        Sorteio sorteio = new Sorteio();
        sorteio.setId("sorteio-encerrado");
        sorteio.setStatus(SorteioStatusEnum.ENCERRADO);
        sorteio.setNumeros_sorteados_qtd(75L); // Simula que todas as bolas já foram sorteadas

        // Configuração de uma lista vazia de cartelas (não relevante para este teste)
        List<Cartela> cartelasDoSorteio = new ArrayList<>();

        // Execução do método
        int resultado = sorteioService.sorteiaBola(sorteio, cartelasDoSorteio);

        // Validação: o método deve retornar 99, indicando que o sorteio já está encerrado
        assertEquals(99, resultado, "O método deveria retornar 99 para sorteios encerrados.");
    }

    @Test
    public void deveriaRetornarZeroQuandoSorteioNaoEncerradoENaoFinalizado() {
        // Configuração do sorteio
        Sorteio sorteio = new Sorteio();
        sorteio.setId("sorteio-ativo");
        sorteio.setStatus(SorteioStatusEnum.EM_PROGRESSO_QUARTA);
        sorteio.setNumeros_sorteados_qtd(10L); // Simula que já foram sorteadas 10 bolas
        sorteio.setLista_numeros_sorteados(new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)));
        sorteio.setUpdatedAt(LocalDateTime.now());

        // Configuração de uma lista de cartelas (não relevante para este teste)
        List<Cartela> cartelasDoSorteio = new ArrayList<>();

        // Mock do repositório para salvar o sorteio
        when(sorteioRepository.save(any(Sorteio.class))).thenReturn(sorteio);

        // Mock do método sorteiaMaisUmaBolinhaNoSorteio
        SorteioService spySorteioService = spy(sorteioService);
        doReturn(11).when(spySorteioService).sorteiaMaisUmaBolinhaNoSorteio(any(Sorteio.class));

        // Execução do método
        int resultado = spySorteioService.sorteiaBola(sorteio, cartelasDoSorteio);

        // Validação: o método deve retornar 0, indicando que o sorteio não foi encerrado
        assertEquals(0, resultado, "O método deveria retornar 0 para sorteios não encerrados e não finalizados.");
    }

}
