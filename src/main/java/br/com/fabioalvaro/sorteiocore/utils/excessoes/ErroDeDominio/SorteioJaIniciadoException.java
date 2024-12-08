package br.com.fabioalvaro.sorteiocore.utils.excessoes.ErroDeDominio;

public class SorteioJaIniciadoException extends RuntimeException {

    public SorteioJaIniciadoException() {
        super("Sorteio Já iniciado.");
    }


}