package br.com.fabioalvaro.sorteiocore.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.fabioalvaro.sorteiocore.model.Cartela;
import br.com.fabioalvaro.sorteiocore.model.Jogador;
import br.com.fabioalvaro.sorteiocore.model.MovimentoFinanceiro;
import br.com.fabioalvaro.sorteiocore.repository.MovimentoFinanceiroRepository;

@Service
public class MovimentoFinanceiroService {
    private static final Logger logger = LoggerFactory.getLogger(SorteioService.class);

    @Autowired
    private MovimentoFinanceiroRepository movimentoFinanceiroRepository;

    @Autowired
    private JogadorService jogadorService;

    public List<MovimentoFinanceiro> findAll() {
        return movimentoFinanceiroRepository.findAll();
    }

    public Optional<MovimentoFinanceiro> findById(String id) {
        return movimentoFinanceiroRepository.findById(id);
    }

    public MovimentoFinanceiro save(MovimentoFinanceiro movimentoFinanceiro) {
        return movimentoFinanceiroRepository.save(movimentoFinanceiro);
    }

    public void deleteById(String id) {
        movimentoFinanceiroRepository.deleteById(id);
    }

    public List<MovimentoFinanceiro> findByJogadorId(String jogadorId) {
        return movimentoFinanceiroRepository.findByOrigem(jogadorId);
    }

    @SuppressWarnings("unused")
    public Boolean adicionaCredito(Cartela cartela, double valor) {
        MovimentoFinanceiro movimentoFinanceiro = new MovimentoFinanceiro();
        movimentoFinanceiro.setCreatedAt(LocalDateTime.now());
        movimentoFinanceiro.setMensagem("Premiação de cartela=" + cartela.getId());
        movimentoFinanceiro.setTipo("CREDITO");
        movimentoFinanceiro.setValor(valor);
        movimentoFinanceiro.setOrigem(cartela.getJogadorId());
        movimentoFinanceiro.setDestino(cartela.getJogadorId());
        MovimentoFinanceiro retorno = movimentoFinanceiroRepository.save(movimentoFinanceiro);
        if (retorno != null) {
            // O retorno não é nulo, então a operação foi bem-sucedida
            logger.info("Movimento financeiro salvo com sucesso.");
            // Faça outras operações necessárias com o objeto retorno
            Optional<Jogador> jogador;
            jogador = jogadorService.buscarJogadorById(cartela.getJogadorId());
            if (jogador.isPresent()) {
                jogador.get().setSaldo(jogador.get().getSaldo() + valor);
                jogadorService.save(jogador.get());
            }
            return true;
        } else {
            // O retorno é nulo, então algo deu errado
            logger.error("Falha ao salvar o movimento financeiro.");
            // Trate o erro de acordo
            return false;
        }

    }
}