package br.com.fabioalvaro.sorteiocore.utils.excessoes.ErroDeDominio;

public class ErroClientSaldoException extends RuntimeException {

    public ErroClientSaldoException() {
        super("Erro ao chamar a api de saldo!");
    }

    public ErroClientSaldoException(String mensagem) {
        super(mensagem);
    }

}