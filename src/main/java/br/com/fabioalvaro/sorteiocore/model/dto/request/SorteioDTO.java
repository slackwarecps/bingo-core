package br.com.fabioalvaro.sorteiocore.model.dto.request;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.validation.constraints.NotNull;
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
    @NotNull(message = "O campo data é obrigatório")
    private String local;
    @NotNull(message = "O campo nome é obrigatório")
    private String nome;
    private Long numeros_sorteados_qtd;
    private List<Integer> lista_numeros_sorteados;

}
