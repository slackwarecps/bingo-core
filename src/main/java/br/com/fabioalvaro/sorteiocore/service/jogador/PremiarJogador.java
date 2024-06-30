package br.com.fabioalvaro.sorteiocore.service.jogador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.fabioalvaro.sorteiocore.model.Cartela;
import br.com.fabioalvaro.sorteiocore.model.Jogador;
import br.com.fabioalvaro.sorteiocore.model.financeiro.MovimentoFinanceiro;
import br.com.fabioalvaro.sorteiocore.service.financeiro.MovimentoFinanceiroService;

@Service
public class PremiarJogador {
    @Autowired
    private JogadorService jogadorService;

    @Autowired
    private MovimentoFinanceiroService movimentoFinanceiroService;

    public void premiaJogador(Cartela cartela, Jogador jogador) {
        if (!cartela.getGanhouQuadra()) {
            throw new IllegalArgumentException("A cartela não é premiada com Quadra.");
        }
        // Adiciona o prêmio ao saldo do jogador
        double premio = 100.00;
        jogador.setSaldo(jogador.getSaldo() + premio);
        jogadorService.updateJogador(jogador);

        // Lança no movimento financeiro do cliente
        MovimentoFinanceiro movimentoFinanceiro = new MovimentoFinanceiro();
        movimentoFinanceiro.setOrigem(jogador.getId());
        movimentoFinanceiro.setValor(premio);
        movimentoFinanceiro.setMensagem("Prêmio da cartela " + cartela.getId());
        movimentoFinanceiroService.saveMovimentoFinanceiro(movimentoFinanceiro);
    }

}
