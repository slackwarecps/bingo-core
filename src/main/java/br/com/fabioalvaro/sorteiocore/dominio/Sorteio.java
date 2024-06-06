package br.com.fabioalvaro.sorteiocore.dominio;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "sorteio")
@Data
public class Sorteio {
    @Id
    private String id;

    private LocalDateTime createAt;
    private String local;
    private Long numeros_sorteados_qtd;
    private List<Integer> lista_numeros_sorteados;
}
