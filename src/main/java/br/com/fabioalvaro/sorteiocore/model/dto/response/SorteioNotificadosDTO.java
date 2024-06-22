package br.com.fabioalvaro.sorteiocore.model.dto.response;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SorteioNotificadosDTO {
    private String id;
    private String local;
    private LocalDateTime createAt;
    private Long numeros_sorteados_qtd;
    private int cartelasQtd;
    private int ganharamQuadra;
    private int ganharamQuina;
    private int ganharamFull;
    private String status; // ENCERRADO/ATIVO/EXCLUIDO
    List<CartelaNotificadosDTO> listaDeCartelas;
}
