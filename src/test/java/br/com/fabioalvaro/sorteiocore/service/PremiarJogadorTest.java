package br.com.fabioalvaro.sorteiocore.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.time.LocalDateTime;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.fabioalvaro.sorteiocore.model.Cartela;
import br.com.fabioalvaro.sorteiocore.model.Jogador;
import br.com.fabioalvaro.sorteiocore.model.Sorteio;
import br.com.fabioalvaro.sorteiocore.model.financeiro.MovimentoFinanceiro;
import br.com.fabioalvaro.sorteiocore.service.financeiro.MovimentoFinanceiroService;
import br.com.fabioalvaro.sorteiocore.service.jogador.JogadorService;
import br.com.fabioalvaro.sorteiocore.service.jogador.PremiarJogador;

@SpringBootTest
public class PremiarJogadorTest {
    @InjectMocks
    private PremiarJogador premiarJogador;

    @Mock
    private JogadorService jogadorService;

    @Mock
    private MovimentoFinanceiroService movimentoFinanceiroService;

    private Jogador jogador;
    private Cartela cartela;
    private Cartela cartela1;
    private Sorteio sorteio;

    @BeforeEach
    public void setUp() {

        // Cadastra Sorteio
        String sorteioId = "123";
        sorteio = new Sorteio();
        sorteio.setId(sorteioId);
        sorteio.setLocal("Local teste");
        sorteio.setNumeros_sorteados_qtd(0L);
        sorteio.setStatus("ATIVO");

        MockitoAnnotations.openMocks(this);
        jogador = new Jogador();
        jogador.setId("jogador1");
        jogador.setSaldo(0.00);

        cartela = new Cartela();
        cartela.setJogadorId("jogador1");
        cartela1 = new Cartela();
        cartela1.setId("1");
        cartela1.setSorteioId(sorteioId);
        cartela1.setVendedorId("10");
        // cartela1.setJogadorId("1000");
        cartela1.setCreatedAt(LocalDateTime.now());
        // List<Integer> c1l1 = new ArrayList<>();
        cartela1.setLinha01(Arrays.asList(1, 2, 3, 4, 5));
        cartela1.setLinha02(Arrays.asList(6, 7, 8, 9, 10));
        cartela1.setLinha03(Arrays.asList(11, 12, 13, 14, 15));
        cartela1.setSorteioId(sorteio.getId());
        cartela1.setGanhouCheia(false);
        cartela1.setGanhouQuadra(false);
        cartela1.setGanhouQuina(false);
    }

    @Test
    public void Testa() {
        cartela1.setGanhouQuadra(true);

        premiarJogador.premiaJogador(cartela1, jogador);

        // Verifica se o saldo foi atualizado

        assert (jogador.getSaldo() == 100.00);

    }

    @Test
    public void testPremiaJogador() {
        premiarJogador.premiaJogador(cartela, jogador);

        // Verifica se o saldo foi atualizado

        assert (jogador.getSaldo() == 100.00);

        // Verifica se o movimento financeiro foi salvo
        verify(movimentoFinanceiroService, times(1)).saveMovimentoFinanceiro(any(MovimentoFinanceiro.class));
    }

    @Test
    public void testPremiaJogadorComCartelaNaoPremiada() {
        cartela.setGanhouQuadra(false);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            premiarJogador.premiaJogador(cartela, jogador);
        });

        String expectedMessage = "A cartela não é premiada.";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));

        // Verifica se o saldo não foi atualizado
        verify(jogadorService, times(0)).updateJogador(jogador);

        // Verifica se o movimento financeiro não foi salvo
        verify(movimentoFinanceiroService, times(0)).saveMovimentoFinanceiro(any(MovimentoFinanceiro.class));
    }
}