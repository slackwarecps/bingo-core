package br.com.fabioalvaro.sorteiocore.utils.excessoes.ErroDeDominio;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(Long id) {
        super(String.format("Usuario %d não encontrado.", id));
    }

}