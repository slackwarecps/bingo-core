package br.com.fabioalvaro.sorteiocore.utils.excessoes.ErroDeDominio;

public class JogadorNaoExisteException  extends RuntimeException {
    public JogadorNaoExisteException() {
        super("jogador Não existe!");
    }
}
