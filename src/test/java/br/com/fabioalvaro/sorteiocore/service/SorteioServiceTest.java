package br.com.fabioalvaro.sorteiocore.service;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.fabioalvaro.sorteiocore.dominio.Cartela;
import br.com.fabioalvaro.sorteiocore.dominio.Sorteio;
import br.com.fabioalvaro.sorteiocore.repository.CartelaRepository;
import br.com.fabioalvaro.sorteiocore.repository.SorteioRepository;

@SpringBootTest
public class SorteioServiceTest {
    @Mock
    private SorteioRepository sorteioRepository;

    @Mock
    private CartelaRepository cartelaRepository;

    @InjectMocks
    private SorteioService sorteioService;

    @InjectMocks
    private CartelaService cartelaService;

    private Sorteio sorteio;
    private List<Cartela> listaDeCartelas;

    @BeforeEach
    public void setUp() {
        // Cadastra Sorteio
        String sorteioId = "123";
        sorteio = new Sorteio();
        sorteio.setId(sorteioId);
        sorteio.setLocal("Local teste");
        sorteio.setNumeros_sorteados_qtd(0L);
        sorteio.setStatus("ATIVO");
        // sorteio.setLista_numeros_sorteados(Arrays.asList(1, 2, 3, 4, 5));
        sorteio.setLista_numeros_sorteados(new ArrayList<>());

        Cartela cartela1 = new Cartela();
        cartela1.setId("cartela1");
        cartela1.setSorteioId(sorteioId);
        cartela1.setLinha01(Arrays.asList(1, 2, 3, 4, 5));
        cartela1.setLinha02(Arrays.asList(6, 7, 8, 9, 10));
        cartela1.setLinha03(Arrays.asList(11, 12, 13, 14, 15));
        cartela1.setSorteioId(sorteio.getId());

        Cartela cartela2 = new Cartela();
        cartela2.setId("cartela2");
        cartela2.setSorteioId(sorteioId);
        cartela2.setLinha01(Arrays.asList(16, 17, 18, 19, 20));
        cartela2.setLinha02(Arrays.asList(21, 22, 23, 24, 25));
        cartela2.setLinha03(Arrays.asList(26, 27, 28, 29, 30));
        cartela2.setSorteioId(sorteio.getId());

        Cartela cartela3 = new Cartela();
        cartela3.setId("cartela3");
        cartela3.setSorteioId(sorteioId);
        cartela3.setLinha01(Arrays.asList(31, 32, 33, 34, 35));
        cartela3.setLinha02(Arrays.asList(36, 37, 38, 39, 40));
        cartela3.setLinha03(Arrays.asList(41, 42, 43, 44, 45));
        cartela3.setSorteioId(sorteio.getId());

        listaDeCartelas = Arrays.asList(cartela1, cartela2, cartela3);
        // quando solicitar cartelas no repository a lista criada vai ser retornarda.
        when(cartelaRepository.findBySorteioId(sorteio.getId())).thenReturn(listaDeCartelas);
        // quando salvar o sorteio no repository o objeto vai ser salvo.
        when(sorteioRepository.save(any(Sorteio.class))).thenReturn(sorteio);

    }

    @Test
    @Tag("sorteio")
    public void deveAumentarAQtdDeBolasSorteadas() {

        // Act
        sorteioService.sorteiaBola(sorteio, listaDeCartelas);

        // Assert
        assertFalse(sorteio.getLista_numeros_sorteados().isEmpty());
        verify(sorteioRepository, times(1)).save(sorteio);
        verify(cartelaRepository, times(1)).findBySorteioId(sorteio.getId());

    }
}