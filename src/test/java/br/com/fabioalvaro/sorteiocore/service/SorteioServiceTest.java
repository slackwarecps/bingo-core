package br.com.fabioalvaro.sorteiocore.service;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.Arrays;
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

public class SorteioServiceTest {
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



}
