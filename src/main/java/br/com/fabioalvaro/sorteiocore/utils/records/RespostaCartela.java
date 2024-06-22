package br.com.fabioalvaro.sorteiocore.utils.records;

import java.util.List;

public record RespostaCartela(
        String local,
        Double valorCartela,
        List<Integer> linha1,
        List<Integer> linha2,
        List<Integer> linha3,
        String jogadorId,
        String jogadorNome

) {

}
