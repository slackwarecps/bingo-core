package br.com.fabioalvaro.sorteiocore.dominio;

import java.util.List;

import lombok.Data;

@Data
class Linha {
    private boolean premioQuadra;

    public boolean isPremioQuadra() {
        return premioQuadra;
    }

    private List<Integer> numeros;

    public List<Integer> getNumeros() {
        return numeros;
    }

    public void setNumeros(List<Integer> numeros) {
        this.numeros = numeros;
    }

}