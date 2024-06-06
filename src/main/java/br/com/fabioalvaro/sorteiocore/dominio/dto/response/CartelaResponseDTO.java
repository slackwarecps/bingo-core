package br.com.fabioalvaro.sorteiocore.dominio.dto.response;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class CartelaResponseDTO {
    private String id;
    private LocalDateTime criado;
    private String jogador;

    List<Integer> linha01;
    List<Integer> linha02;
    List<Integer> linha03;

}