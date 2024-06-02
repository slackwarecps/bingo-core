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
@Document(collection = "cartela")
@Data
public class Cartela {
    @Id
    private String id;

    private LocalDateTime criado;
    private String jogador;

    List<Integer> linha01;
    List<Integer> linha02;
    List<Integer> linha03;

}