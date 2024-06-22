package br.com.fabioalvaro.sorteiocore.model.dto.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CartelaNotificadosDTO {
    String cartelaId;
    String local;
    Double valorCartela;
    List<Integer> linha1;
    List<Integer> linha2;
    List<Integer> linha3;
    String jogadorId;
    String jogadorNome;
}
