package br.com.fabioalvaro.sorteiocore.utils.excessoes.ErroDeDominio;

public class VendedorNaoExisteException extends RuntimeException {

    public VendedorNaoExisteException() {
        super("Vendedor Nao existe!");
    }


}