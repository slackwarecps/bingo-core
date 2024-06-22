package br.com.fabioalvaro.sorteiocore.model;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.NotNull;
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
@Document(collection = "cartela")
@Data
@ToString
public class Cartela {
    @Id
    private String id;

    private LocalDateTime createdAt;
    @NotNull
    private String jogadorId;

    List<Integer> linha01;
    List<Integer> linha02;
    List<Integer> linha03;

    @NotNull
    private String sorteioId;
    @NotNull
    private String vendedorId;

    private Double valor; // valor da Cartela
    private Boolean ganhouQuadra;
    private Boolean ganhouQuina;
    private Boolean ganhouCheia;
    private String tiraTeimaId;// cartela participou de tirateima?
    private String status; // valores possíveis: "ativa", "cancelada", "

    private Double premioQuadra;// ganhou? sim/nao
    private Double premioQuina;// ganhou?
    private Double premioCheio;// ganhou?

}