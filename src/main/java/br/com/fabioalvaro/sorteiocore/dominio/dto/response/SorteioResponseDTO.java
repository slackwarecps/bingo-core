package br.com.fabioalvaro.sorteiocore.dominio.dto.response;

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
public class SorteioResponseDTO {
    private String id;
    private LocalDateTime createAt;
    private String local;
    private Long numeros_sorteados_qtd;
    private List<Integer> lista_numeros_sorteados;
}
