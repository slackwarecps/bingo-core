package br.com.fabioalvaro.sorteiocore.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.fabioalvaro.sorteiocore.model.Cartela;
import br.com.fabioalvaro.sorteiocore.model.Sorteio;
import br.com.fabioalvaro.sorteiocore.repository.CartelaRepository;
import br.com.fabioalvaro.sorteiocore.repository.SorteioRepository;
import br.com.fabioalvaro.sorteiocore.service.sorteio.SorteioService;

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

                List<Integer> numerosSorteados = new ArrayList<>();

                // Cadastra Sorteio
                String sorteioId = "123";
                sorteio = new Sorteio();
                sorteio.setId(sorteioId);
                sorteio.setLocal("Local teste");
                sorteio.setNumeros_sorteados_qtd(0L);
                sorteio.setStatus("ATIVO");

                numerosSorteados.add(1);
                numerosSorteados.add(2);
                numerosSorteados.add(3);
                sorteio.setLista_numeros_sorteados(numerosSorteados);

                sorteio.setNumeros_sorteados_qtd(3L);
                sorteio.setCartelasQtd(3);

                // sorteio.setLista_numeros_sorteados(new ArrayList<>());

                Cartela cartela1 = new Cartela();
                cartela1.setId("1");
                cartela1.setSorteioId(sorteioId);
                cartela1.setVendedorId("10");
                cartela1.setJogadorId("1000");
                cartela1.setCreatedAt(LocalDateTime.now());
                // List<Integer> c1l1 = new ArrayList<>();
                cartela1.setLinha01(Arrays.asList(1, 2, 3, 4, 5));
                cartela1.setLinha02(Arrays.asList(6, 7, 8, 9, 10));
                cartela1.setLinha03(Arrays.asList(11, 12, 13, 14, 15));
                cartela1.setSorteioId(sorteio.getId());
                cartela1.setGanhouCheia(false);
                cartela1.setGanhouQuadra(false);
                cartela1.setGanhouQuina(false);

                Cartela cartela2 = new Cartela();
                cartela2.setId("2");
                cartela2.setSorteioId(sorteioId);
                cartela2.setVendedorId("10");
                cartela2.setJogadorId("1000");
                cartela2.setCreatedAt(LocalDateTime.now());
                cartela2.setLinha01(Arrays.asList(16, 17, 18, 19, 20));
                cartela2.setLinha02(Arrays.asList(21, 22, 23, 24, 25));
                cartela2.setLinha03(Arrays.asList(26, 27, 28, 29, 30));
                cartela2.setSorteioId(sorteio.getId());
                cartela2.setGanhouCheia(false);
                cartela2.setGanhouQuadra(false);
                cartela2.setGanhouQuina(false);

                Cartela cartela3 = new Cartela();
                cartela3.setId("3");
                cartela3.setSorteioId(sorteioId);
                cartela3.setVendedorId("10");
                cartela3.setJogadorId("1000");
                cartela3.setCreatedAt(LocalDateTime.now());
                cartela3.setLinha01(Arrays.asList(11, 12, 13, 14, 15));
                cartela3.setLinha02(Arrays.asList(36, 37, 38, 39, 40));
                cartela3.setLinha03(Arrays.asList(41, 42, 43, 44, 45));
                cartela3.setSorteioId(sorteio.getId());
                cartela3.setGanhouCheia(false);
                cartela3.setGanhouQuadra(false);
                cartela3.setGanhouQuina(false);

                listaDeCartelas = Arrays.asList(cartela1, cartela2, cartela3);

                // quando solicitar cartelas no repository a lista criada vai ser retornarda.
                when(cartelaRepository.findBySorteioId(sorteio.getId())).thenReturn(listaDeCartelas);
                // quando salvar o sorteio no repository o objeto vai ser salvo.

                when(sorteioRepository.findById(sorteio.getId())).thenReturn(Optional.of(sorteio));
                when(sorteioRepository.save(any(Sorteio.class))).thenReturn(sorteio);

        }

        @Test
        @Tag("sorteio")
        public void deveAumentarAQtdDeBolasSorteadas() {
                // Arrange
                int initialCount = sorteio.getNumeros_sorteados_qtd().intValue();
                // Act
                sorteioService.sorteiaBola(sorteio, listaDeCartelas);

                // Assert
                assertEquals(initialCount + 1, sorteio.getNumeros_sorteados_qtd().intValue());

        }

        @Test
        @Tag("sorteio")
        public void deveAdicionarMaisUmNumeroNaLista() {
                // Arrange

                int tamanhoLista = sorteio.getLista_numeros_sorteados().size();
                // Act
                sorteioService.sorteiaBola(sorteio, listaDeCartelas);
                // Assert

                assertEquals(tamanhoLista + 1, sorteio.getLista_numeros_sorteados().size());

        }

        @Test
        @Tag("sorteio")
        public void CartelaDeveGanharQuadra() {
                // Arrange
                List<Integer> numerosSorteados = new ArrayList<>();
                numerosSorteados.add(1);
                numerosSorteados.add(2);
                numerosSorteados.add(3);
                numerosSorteados.add(4);
                sorteio.setLista_numeros_sorteados(numerosSorteados);
                sorteio.setNumeros_sorteados_qtd(4L);

                // Act
                sorteioService.sorteiaBola(sorteio, listaDeCartelas);
                // Assert

                assertEquals(1, sorteio.getGanharamQuadra());

        }

        @Test
        @Tag("sorteio")
        public void naoDeveriaSortearEncerrado() {
                // Arrange
                int tamanhoLista = sorteio.getLista_numeros_sorteados().size();
                sorteio.setStatus("ENCERRADO");

                // Act
                sorteioService.sorteiaBola(sorteio, listaDeCartelas);
                // Assert

                assertEquals(tamanhoLista, sorteio.getNumeros_sorteados_qtd().intValue());
                verify(sorteioRepository, times(0)).save(sorteio);

        }

        @Test
        @Tag("sorteio")
        public void deveEncerrarSorteio() {
                // Sorteio
                sorteio.setNumeros_sorteados_qtd(0L);
                sorteio.setLista_numeros_sorteados(new ArrayList<>());
                List<Integer> lista1 = IntStream.rangeClosed(1, 70)
                                .boxed()
                                .collect(Collectors.toList());
                sorteio.setLista_numeros_sorteados(lista1);
                sorteio.setGanharamFull(0);
                sorteio.setNumeros_sorteados_qtd(70L);
                sorteio.setStatus("ATIVO");

                // Cartela
                Cartela cartela1 = new Cartela();
                cartela1.setId("1");
                cartela1.setSorteioId(sorteio.getId());
                cartela1.setVendedorId("10");
                cartela1.setJogadorId("1000");
                cartela1.setCreatedAt(LocalDateTime.now());
                cartela1.setLinha01(IntStream.rangeClosed(1, 5)
                                .boxed()
                                .collect(Collectors.toList()));
                cartela1.setLinha02(IntStream.rangeClosed(6, 10)
                                .boxed()
                                .collect(Collectors.toList()));
                cartela1.setLinha03(IntStream.rangeClosed(11, 15)
                                .boxed()
                                .collect(Collectors.toList()));
                cartela1.setSorteioId(sorteio.getId());
                cartela1.setGanhouCheia(false);
                cartela1.setGanhouQuadra(false);
                cartela1.setGanhouQuina(false);

                // Remove cartela3 da lista
                List<Cartela> listaDeCartelasLocal;
                listaDeCartelasLocal = Arrays.asList(cartela1);

                // Act
                int qtd = 70;
                while (qtd < 75 && sorteio.getStatus() == "ATIVO") {

                        sorteioService.sorteiaBola(sorteio, listaDeCartelasLocal);
                        qtd = sorteio.getNumeros_sorteados_qtd().intValue();
                        System.out.println(qtd);
                }

                // Assert

                assertEquals("ENCERRADO", sorteio.getStatus());

        }

        @Test
        public void deveSortearUmNumeroValido() {
                // Arrange
                List<Integer> numerosSorteados = sorteio.getLista_numeros_sorteados();
                for (int i = 1; i <= 10; i++) {
                        numerosSorteados.add(i);
                }
                sorteio.setLista_numeros_sorteados(numerosSorteados);

                // Act
                Integer numeroSorteado = sorteioService.sorteiaNumero(sorteio);

                // Assert
                assertNotNull(numeroSorteado);
                assertTrue(numeroSorteado >= 1 && numeroSorteado <= 75);
                assertFalse(numerosSorteados.contains(numeroSorteado));
        }

        @Test
        public void naoDeveriaSortearNenhumNumero() {
                // Arrange
                List<Integer> numerosSorteados = sorteio.getLista_numeros_sorteados();
                for (int i = 1; i <= 75; i++) {
                        numerosSorteados.add(i);
                }
                sorteio.setLista_numeros_sorteados(numerosSorteados);

                // Act
                Integer numeroSorteado = sorteioService.sorteiaNumero(sorteio);

                // Assert
                assertEquals(-1, numeroSorteado);
        }

        @Test
        public void testUmaPessoaGanhaCartelaCheia() {
                String sorteioId = "999";
                Sorteio sorteioCheio = new Sorteio();
                sorteioCheio.setLista_numeros_sorteados(new ArrayList<>());
                sorteioCheio.setNumeros_sorteados_qtd(15L);
                sorteioCheio.setId(sorteioId);
                sorteioCheio.setLocal("Local teste 999");
                List<Integer> lista1 = IntStream.rangeClosed(1, 15)
                                .boxed()
                                .collect(Collectors.toList());
                sorteioCheio.setLista_numeros_sorteados(lista1);
                sorteioCheio.setNumeros_sorteados_qtd(15L);
                sorteioCheio.setStatus("ATIVO");
                sorteioCheio.setGanharamFull(0);
                sorteioCheio.setCartelasQtd(1);

                Cartela cartela = new Cartela();
                cartela.setId("1");
                cartela.setSorteioId(sorteioId);
                cartela.setVendedorId("10");
                cartela.setJogadorId("1000");
                cartela.setGanhouCheia(false);
                cartela.setGanhouQuadra(false);
                cartela.setGanhouQuina(false);
                cartela.setCreatedAt(LocalDateTime.now());
                cartela.setSorteioId(sorteioCheio.getId());
                cartela.setLinha01(IntStream.rangeClosed(1, 5)
                                .boxed()
                                .collect(Collectors.toList()));
                cartela.setLinha02(IntStream.rangeClosed(6, 10)
                                .boxed()
                                .collect(Collectors.toList()));
                cartela.setLinha03(IntStream.rangeClosed(11, 15)
                                .boxed()
                                .collect(Collectors.toList()));
                List<Cartela> cartelasDoSorteio = List.of(cartela);

                // quando solicitar cartelas no repository a lista criada vai ser retornarda.
                when(cartelaRepository.findBySorteioId(sorteioCheio.getId())).thenReturn(listaDeCartelas);
                // quando salvar o sorteio no repository o objeto vai ser salvo.

                when(sorteioRepository.findById(sorteioCheio.getId())).thenReturn(Optional.of(sorteioCheio));
                when(sorteioRepository.save(any(Sorteio.class))).thenReturn(sorteioCheio);
                when(cartelaRepository.findById(cartela.getId())).thenReturn(Optional.of(cartela));
                when(cartelaRepository.save(any(Cartela.class))).thenReturn(cartela);

                sorteioService.sorteiaBola(sorteioCheio, cartelasDoSorteio);
                assertEquals(1, sorteioCheio.getGanharamFull());

        }

        @Test
        public void deveEncerrarSorteioComUmaCartelaCheia() {
                String sorteioId = "999";
                Sorteio sorteioCheio = new Sorteio();
                sorteioCheio.setLista_numeros_sorteados(new ArrayList<>());
                sorteioCheio.setNumeros_sorteados_qtd(15L);
                sorteioCheio.setId(sorteioId);
                sorteioCheio.setLocal("Local teste 999");
                List<Integer> lista1 = IntStream.rangeClosed(1, 15)
                                .boxed()
                                .collect(Collectors.toList());
                sorteioCheio.setLista_numeros_sorteados(lista1);
                sorteioCheio.setNumeros_sorteados_qtd(15L);
                sorteioCheio.setStatus("ATIVO");
                sorteioCheio.setGanharamFull(0);
                sorteioCheio.setCartelasQtd(1);

                Cartela cartela = new Cartela();
                cartela.setId("1");
                cartela.setSorteioId(sorteioId);
                cartela.setVendedorId("10");
                cartela.setJogadorId("1000");
                cartela.setGanhouCheia(false);
                cartela.setGanhouQuadra(false);
                cartela.setGanhouQuina(false);
                cartela.setCreatedAt(LocalDateTime.now());
                cartela.setSorteioId(sorteioCheio.getId());
                cartela.setLinha01(IntStream.rangeClosed(1, 5)
                                .boxed()
                                .collect(Collectors.toList()));
                cartela.setLinha02(IntStream.rangeClosed(6, 10)
                                .boxed()
                                .collect(Collectors.toList()));
                cartela.setLinha03(IntStream.rangeClosed(11, 15)
                                .boxed()
                                .collect(Collectors.toList()));
                List<Cartela> cartelasDoSorteio = List.of(cartela);

                // quando solicitar cartelas no repository a lista criada vai ser retornarda.
                when(cartelaRepository.findBySorteioId(sorteioCheio.getId())).thenReturn(listaDeCartelas);
                // quando salvar o sorteio no repository o objeto vai ser salvo.

                when(sorteioRepository.findById(sorteioCheio.getId())).thenReturn(Optional.of(sorteioCheio));
                when(sorteioRepository.save(any(Sorteio.class))).thenReturn(sorteioCheio);
                when(cartelaRepository.findById(cartela.getId())).thenReturn(Optional.of(cartela));
                when(cartelaRepository.save(any(Cartela.class))).thenReturn(cartela);

                sorteioService.sorteiaBola(sorteioCheio, cartelasDoSorteio);
                assertEquals("ENCERRADO", sorteioCheio.getStatus());

        }

        @Test
        public void deveApenas1PessoaGanharComDuasCartelasConcorrendo() {
                String sorteioId = "999";
                Sorteio sorteioCheio = new Sorteio();
                sorteioCheio.setLista_numeros_sorteados(new ArrayList<>());
                sorteioCheio.setNumeros_sorteados_qtd(15L);
                sorteioCheio.setId(sorteioId);
                sorteioCheio.setLocal("Local teste 999");
                List<Integer> lista1 = IntStream.rangeClosed(1, 15)
                                .boxed()
                                .collect(Collectors.toList());
                sorteioCheio.setLista_numeros_sorteados(lista1);
                sorteioCheio.setNumeros_sorteados_qtd(15L);
                sorteioCheio.setStatus("ATIVO");
                sorteioCheio.setGanharamFull(0);
                sorteioCheio.setCartelasQtd(1);

                Cartela cartela1 = new Cartela();
                cartela1.setId("1");
                cartela1.setSorteioId(sorteioId);
                cartela1.setVendedorId("10");
                cartela1.setJogadorId("1000");
                cartela1.setGanhouCheia(false);
                cartela1.setGanhouQuadra(false);
                cartela1.setGanhouQuina(false);
                cartela1.setCreatedAt(LocalDateTime.now());
                cartela1.setSorteioId(sorteioCheio.getId());
                cartela1.setLinha01(IntStream.rangeClosed(1, 5)
                                .boxed()
                                .collect(Collectors.toList()));
                cartela1.setLinha02(IntStream.rangeClosed(6, 10)
                                .boxed()
                                .collect(Collectors.toList()));
                cartela1.setLinha03(IntStream.rangeClosed(21, 25)
                                .boxed()
                                .collect(Collectors.toList()));

                Cartela cartela2 = new Cartela();
                cartela2.setId("2");
                cartela2.setSorteioId(sorteioId);
                cartela2.setVendedorId("10");
                cartela2.setJogadorId("1000");
                cartela2.setGanhouCheia(false);
                cartela2.setGanhouQuadra(false);
                cartela2.setGanhouQuina(false);
                cartela2.setCreatedAt(LocalDateTime.now());
                cartela2.setSorteioId(sorteioCheio.getId());
                cartela2.setLinha01(IntStream.rangeClosed(1, 5)
                                .boxed()
                                .collect(Collectors.toList()));
                cartela2.setLinha02(IntStream.rangeClosed(6, 10)
                                .boxed()
                                .collect(Collectors.toList()));
                cartela2.setLinha03(IntStream.rangeClosed(11, 15)
                                .boxed()
                                .collect(Collectors.toList()));

                List<Cartela> cartelasDoSorteio = List.of(cartela1, cartela2);

                // quando solicitar cartelas no repository a lista criada vai ser retornarda.
                when(cartelaRepository.findBySorteioId(sorteioCheio.getId())).thenReturn(listaDeCartelas);
                // quando salvar o sorteio no repository o objeto vai ser salvo.

                when(sorteioRepository.findById(sorteioCheio.getId())).thenReturn(Optional.of(sorteioCheio));
                when(sorteioRepository.save(any(Sorteio.class))).thenReturn(sorteioCheio);
                when(cartelaRepository.findById(cartela2.getId())).thenReturn(Optional.of(cartela2));
                when(cartelaRepository.save(any(Cartela.class))).thenReturn(cartela2);

                sorteioService.sorteiaBola(sorteioCheio, cartelasDoSorteio);
                assertEquals(1, sorteioCheio.getGanharamFull());
        }

        @Test
        public void testDuasPessoasGanhamCartelaCheia() {
                String sorteioId = "999";
                Sorteio sorteioCheio = new Sorteio();
                sorteioCheio.setLista_numeros_sorteados(new ArrayList<>());
                sorteioCheio.setNumeros_sorteados_qtd(15L);
                sorteioCheio.setId(sorteioId);
                sorteioCheio.setLocal("Local teste 999");
                List<Integer> lista1 = IntStream.rangeClosed(1, 15)
                                .boxed()
                                .collect(Collectors.toList());
                sorteioCheio.setLista_numeros_sorteados(lista1);
                sorteioCheio.setNumeros_sorteados_qtd(15L);
                sorteioCheio.setStatus("ATIVO");
                sorteioCheio.setGanharamFull(0);
                sorteioCheio.setCartelasQtd(1);

                Cartela cartela1 = new Cartela();
                cartela1.setId("1");
                cartela1.setSorteioId(sorteioId);
                cartela1.setVendedorId("10");
                cartela1.setJogadorId("1000");
                cartela1.setGanhouCheia(false);
                cartela1.setGanhouQuadra(false);
                cartela1.setGanhouQuina(false);
                cartela1.setCreatedAt(LocalDateTime.now());
                cartela1.setSorteioId(sorteioCheio.getId());
                cartela1.setLinha01(IntStream.rangeClosed(1, 5)
                                .boxed()
                                .collect(Collectors.toList()));
                cartela1.setLinha02(IntStream.rangeClosed(6, 10)
                                .boxed()
                                .collect(Collectors.toList()));
                cartela1.setLinha03(IntStream.rangeClosed(11, 15)
                                .boxed()
                                .collect(Collectors.toList()));

                Cartela cartela2 = new Cartela();
                cartela2.setId("2");
                cartela2.setSorteioId(sorteioId);
                cartela2.setVendedorId("10");
                cartela2.setJogadorId("1000");
                cartela2.setGanhouCheia(false);
                cartela2.setGanhouQuadra(false);
                cartela2.setGanhouQuina(false);
                cartela2.setCreatedAt(LocalDateTime.now());
                cartela2.setSorteioId(sorteioCheio.getId());
                cartela2.setLinha01(IntStream.rangeClosed(1, 5)
                                .boxed()
                                .collect(Collectors.toList()));
                cartela2.setLinha02(IntStream.rangeClosed(6, 10)
                                .boxed()
                                .collect(Collectors.toList()));
                cartela2.setLinha03(IntStream.rangeClosed(11, 15)
                                .boxed()
                                .collect(Collectors.toList()));

                List<Cartela> cartelasDoSorteio = List.of(cartela1, cartela2);

                // quando solicitar cartelas no repository a lista criada vai ser retornarda.
                when(cartelaRepository.findBySorteioId(sorteioCheio.getId())).thenReturn(listaDeCartelas);
                // quando salvar o sorteio no repository o objeto vai ser salvo.

                when(sorteioRepository.findById(sorteioCheio.getId())).thenReturn(Optional.of(sorteioCheio));
                when(sorteioRepository.save(any(Sorteio.class))).thenReturn(sorteioCheio);
                when(cartelaRepository.findById(cartela2.getId())).thenReturn(Optional.of(cartela2));
                when(cartelaRepository.save(any(Cartela.class))).thenReturn(cartela2);

                sorteioService.sorteiaBola(sorteioCheio, cartelasDoSorteio);
                assertEquals(2, sorteioCheio.getGanharamFull());
        }

}