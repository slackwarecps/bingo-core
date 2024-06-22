package br.com.fabioalvaro.sorteiocore.model;

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
@Document(collection = "tira_teima")
@Data
public class Tirateima {
    @Id
    private String id;
    private LocalDateTime createAt;

    private String sorteioId;
    private String vencedorId;
    private String observacao;
    private String modo; // manual, automatico
    private List<Jogador> participantes;
}
