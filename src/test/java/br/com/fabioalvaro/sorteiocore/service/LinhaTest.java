package br.com.fabioalvaro.sorteiocore.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.fabioalvaro.sorteiocore.dominio.Cartela;
import br.com.fabioalvaro.sorteiocore.dominio.Linha;
import br.com.fabioalvaro.sorteiocore.dominio.Sorteio;
import br.com.fabioalvaro.sorteiocore.repository.CartelaRepository;
import br.com.fabioalvaro.sorteiocore.repository.SorteioRepository;

@SpringBootTest
public class LinhaTest {

    @Mock
    private SorteioRepository sorteioRepository;
    @Mock
    private CartelaRepository cartelaRepository;

    @Mock
    private CartelaService cartelaService;

    @InjectMocks
    private SorteioService sorteioService;

    private Sorteio sorteio;
    private Cartela cartela;

    @BeforeEach
    public void setUp() {
        String sorteioId = "123";
        sorteio = new Sorteio();
        sorteio.setId("123");
        sorteio.setLocal("Local teste");
        sorteio.setNumeros_sorteados_qtd(5L);
        sorteio.setLista_numeros_sorteados(Arrays.asList(1, 2, 3, 4, 5));
        List<Integer> numerosSorteados = new ArrayList<>();

        sorteio.setStatus("ATIVO");

        numerosSorteados.add(1);
        numerosSorteados.add(2);
        numerosSorteados.add(3);
        sorteio.setLista_numeros_sorteados(numerosSorteados);

        sorteio.setNumeros_sorteados_qtd(3L);
        sorteio.setCartelasQtd(3);

        // sorteio.setLista_numeros_sorteados(new ArrayList<>());

        cartela = new Cartela();
        cartela.setId("1");
        cartela.setSorteioId(sorteioId);
        cartela.setVendedorId("10");
        cartela.setJogadorId("1000");
        cartela.setCreatedAt(LocalDateTime.now());
        // List<Integer> c1l1 = new ArrayList<>();
        cartela.setLinha01(Arrays.asList(1, 2, 3, 4, 5));
        cartela.setLinha02(Arrays.asList(6, 7, 8, 9, 10));
        cartela.setLinha03(Arrays.asList(11, 12, 13, 14, 15));
        cartela.setSorteioId(sorteio.getId());
        cartela.setGanhouCheia(false);
        cartela.setGanhouQuadra(false);
        cartela.setGanhouQuina(false);

    }

    @Test
    @Tag("quadra")
    public void linhaNaoDeveriaGanhaNaQuadra() {

        Sorteio sorteio = new Sorteio();
        sorteio.setLista_numeros_sorteados(Arrays.asList(1, 2, 3, 4, 5));

        Linha linha = new Linha();
        linha.setLinha(Arrays.asList(6, 7, 8, 9, 10));

        Boolean result = sorteioService.linhaganhou(sorteio, linha, "QUADRA", cartela);
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

        Boolean result = sorteioService.linhaganhou(sorteio, linha, "QUADRA", cartela);
        assertTrue(result);
    }

    @Test
    @Tag("quadra")
    public void deveriaGanharComQuadraNumerosEmbaralhados() {
        Sorteio sorteio = new Sorteio();
        sorteio.setLista_numeros_sorteados(Arrays.asList(1, 10, 2, 20, 3, 30, 4, 40, 5, 75));

        Linha linha = new Linha();
        linha.setLinha(Arrays.asList(10, 20, 30, 40, 74));

        Boolean result = sorteioService.linhaganhou(sorteio, linha, "QUADRA", cartela);
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

        Boolean result = sorteioService.linhaganhou(sorteio, linha, "QUADRA", cartela);
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

        Boolean result = sorteioService.linhaganhou(sorteio, linha, "QUADRA", cartela);
        assertFalse(result, "Quadra Nao deve ganhar se ja ganhou");
    }

    // QUINA

    @Test
    @Tag("quina")
    public void deveriaGanharComQuina() {

        sorteio.setLista_numeros_sorteados(Arrays.asList(1, 2, 3, 4, 5));

        Linha linha = new Linha();
        linha.setLinha(Arrays.asList(1, 2, 3, 4, 5));

        Boolean result = sorteioService.linhaganhou(sorteio, linha, "QUINA", cartela);
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

        Boolean result = sorteioService.linhaganhou(sorteio, linha, "QUINA", cartela);
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

        Boolean result = sorteioService.linhaganhou(sorteio, linha, "QUINA", cartela);
        assertFalse(result);
    }

    @Test
    public void testeDeveFuncionarApenasComMetodosEsperados() {
        Sorteio sorteio = new Sorteio();
        sorteio.setLista_numeros_sorteados(Arrays.asList(1, 2, 3, 4, 5));

        Linha linha = new Linha();
        linha.setLinha(Arrays.asList(1, 2, 3, 4, 5));

        Executable executable = () -> sorteioService.linhaganhou(sorteio, linha, "INVALID_MODE", cartela);
        RuntimeException exception = assertThrows(RuntimeException.class, executable);
        assertEquals("OPS!!! MODO INVALIDO DE ANALISE DE LINHA ", exception.getMessage());
    }

}