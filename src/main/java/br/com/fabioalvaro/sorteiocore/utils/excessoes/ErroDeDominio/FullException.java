package br.com.fabioalvaro.sorteiocore.utils.excessoes.ErroDeDominio;

public class FullException extends RuntimeException {

    public FullException() {
        super("Erro ao chamar a api de saldo!");
    }

    public FullException(String mensagem) {
        super(mensagem);
    }

}