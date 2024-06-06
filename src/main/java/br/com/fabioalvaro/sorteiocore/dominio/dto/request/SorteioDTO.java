package br.com.fabioalvaro.sorteiocore.dominio.dto.request;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SorteioDTO {

    private String id;
    private LocalDateTime createAt;
    private String local;
    private Long numeros_sorteados_qtd;
    private List<Integer> lista_numeros_sorteados;

}
