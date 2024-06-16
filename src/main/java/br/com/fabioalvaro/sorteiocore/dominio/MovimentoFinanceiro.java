package br.com.fabioalvaro.sorteiocore.dominio;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "movimentoFinanceiro")
@Data
@ToString
public class MovimentoFinanceiro {
    @Id
    private String id;
    private LocalDateTime createdAt;
    private String mensagem;
    private String tipo;
    private Double valor;
    private String origem;
    private String destino;

}
