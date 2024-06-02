package br.com.fabioalvaro.sorteiocore.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ParametrosConfig {

    @Value("${bingo.parametro.maximo_numero}")
    private Integer numero_maximo;

    @Value("${bingo.parametro.minimo_numero}")
    private Integer numero_minimo;

    public Integer getNumero_maximo() {
        return numero_maximo;
    }

    public Integer getNumero_minimo() {
        return numero_minimo;
    }

}