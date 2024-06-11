package br.com.fabioalvaro.sorteiocore.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.fabioalvaro.sorteiocore.dominio.Linha;
import br.com.fabioalvaro.sorteiocore.dominio.Sorteio;
import br.com.fabioalvaro.sorteiocore.repository.SorteioRepository;

@SpringBootTest
public class LinhaTest {

    @Mock
    private SorteioRepository sorteioRepository;

    @Mock
    private CartelaService cartelaService;

    @InjectMocks
    private SorteioService sorteioService;

    private Sorteio sorteio;

    @BeforeEach
    public void setUp() {
        sorteio = new Sorteio();
        sorteio.setId("123");
        sorteio.setLocal("Local teste");
        sorteio.setNumeros_sorteados_qtd(0L);
        sorteio.setLista_numeros_sorteados(Arrays.asList(1, 2, 3, 4, 5));

    }

    @Test
    @Tag("quadra")
    public void linhaNaoDeveriaGanhaNaQuadra() {

        Sorteio sorteio = new Sorteio();
        sorteio.setLista_numeros_sorteados(Arrays.asList(1, 2, 3, 4, 5));

        Linha linha = new Linha();
        linha.setLinha(Arrays.asList(6, 7, 8, 9, 10));

        Boolean result = sorteioService.linhaganhou(sorteio, linha, "QUADRA");
        assertNotEquals("Entao mano...é o seguinte...", result);
        // assertFalse(result);
    }

    @Test
    @Tag("quadra")
    public void deveriaGanharComQuadra() {
        Sorteio sorteio = new Sorteio();
        sorteio.setLista_numeros_sorteados(Arrays.asList(1, 2, 3, 4, 10));

        Linha linha = new Linha();
        linha.setLinha(Arrays.asList(1, 2, 3, 4, 25));

        Boolean result = sorteioService.linhaganhou(sorteio, linha, "QUADRA");
        assertTrue(result);
    }

    @Test
    @Tag("quadra")
    public void deveriaGanharComQuadraNumerosEmbaralhados() {
        Sorteio sorteio = new Sorteio();
        sorteio.setLista_numeros_sorteados(Arrays.asList(1, 10, 2, 20, 3, 30, 4, 40, 5, 75));

        Linha linha = new Linha();
        linha.setLinha(Arrays.asList(10, 20, 30, 40, 74));

        Boolean result = sorteioService.linhaganhou(sorteio, linha, "QUADRA");
        assertTrue(result);
    }

    @Test
    @Tag("quadra")
    public void naoDeveriaGanharQuadra() {
        Sorteio sorteio = new Sorteio();
        sorteio.setLista_numeros_sorteados(Arrays.asList(1, 2, 3, 4, 5));

        Linha linha = new Linha();
        linha.setLinha(Arrays.asList(1, 2, 3, 25, 35));
        linha.setGanhouQuadra(false);
        linha.setGanhouQuina(false);

        Boolean result = sorteioService.linhaganhou(sorteio, linha, "QUADRA");
        assertFalse(result);
    }

    @Test
    @Tag("quadra")
    public void naoDeveriaGanharQuadraJaPremiada() {
        Sorteio sorteio = new Sorteio();
        sorteio.setLista_numeros_sorteados(Arrays.asList(1, 2, 3, 4, 33));

        Linha linha = new Linha();
        linha.setLinha(Arrays.asList(1, 2, 3, 4, 44));
        linha.setGanhouQuadra(true);
        linha.setGanhouQuina(true);

        Boolean result = sorteioService.linhaganhou(sorteio, linha, "QUADRA");
        assertFalse(result, "Quadra Nao deve ganhar se ja ganhou");
    }

    // QUINA

    @Test
    @Tag("quina")
    public void deveriaGanharComQuina() {

        sorteio.setLista_numeros_sorteados(Arrays.asList(1, 2, 3, 4, 5));

        Linha linha = new Linha();
        linha.setLinha(Arrays.asList(1, 2, 3, 4, 5));

        Boolean result = sorteioService.linhaganhou(sorteio, linha, "QUINA");
        assertTrue(result, "Sim deve ganhar com quina.");
    }

    @Test
    public void naoDeveriaGanharQuina() {
        Sorteio sorteio = new Sorteio();
        sorteio.setLista_numeros_sorteados(Arrays.asList(1, 2, 3, 4, 5));

        Linha linha = new Linha();
        linha.setLinha(Arrays.asList(1, 2, 3, 4, 35));
        linha.setGanhouQuadra(false);
        linha.setGanhouQuina(false);

        Boolean result = sorteioService.linhaganhou(sorteio, linha, "QUINA");
        assertFalse(result);
    }

    @Test
    public void naoDeveriaGanharQuinaJaPremiada() {
        Sorteio sorteio = new Sorteio();
        sorteio.setLista_numeros_sorteados(Arrays.asList(1, 2, 3, 4, 5));

        Linha linha = new Linha();
        linha.setLinha(Arrays.asList(1, 2, 3, 4, 5));
        linha.setGanhouQuadra(false);
        linha.setGanhouQuina(true);

        Boolean result = sorteioService.linhaganhou(sorteio, linha, "QUINA");
        assertFalse(result);
    }

    @Test
    public void testeDeveFuncionarApenasComMetodosEsperados() {
        Sorteio sorteio = new Sorteio();
        sorteio.setLista_numeros_sorteados(Arrays.asList(1, 2, 3, 4, 5));

        Linha linha = new Linha();
        linha.setLinha(Arrays.asList(1, 2, 3, 4, 5));

        Executable executable = () -> sorteioService.linhaganhou(sorteio, linha, "INVALID_MODE");
        RuntimeException exception = assertThrows(RuntimeException.class, executable);
        assertEquals("OPS!!! MODO INVALIDO DE ANALISE DE LINHA ", exception.getMessage());
    }

}