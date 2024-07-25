package br.com.fabioalvaro.sorteiocore.utils.excessoes.ErroDeDominio;

public class SorteioNaoExisteException extends RuntimeException {

    public SorteioNaoExisteException() {
        super("Sorteio Não existe!");
    }


}