package br.com.fabioalvaro.sorteiocore.dominio;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import br.com.fabioalvaro.sorteiocore.dominio.enums.TipoSorteioEnum;
import br.com.fabioalvaro.sorteiocore.dominio.enums.TiraTeimaEnum;
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
@Document(collection = "sorteio")
@Data
@ToString
public class Sorteio {
    @Id
    private String id;
    private LocalDateTime createAt;
    private String local;// endereco
    private Long numeros_sorteados_qtd;
    private List<Integer> lista_numeros_sorteados;
    private TipoSorteioEnum tipoSorteio;// [FIXO,DINAMICO]
    private Double valor;
    private String prenda;
    private int cartelasQtd;
    private Double valorDoacaolote1; // valor do lote para compra
    private Double valorDoacaolote;
    private Double valorDoacaolote3;
    private int ganharamQuadra;
    private int ganharamQuina;
    private int ganharamFull;
    private Double valorArrecadado;
    private Double ValorPagoPremios;
    private Double premioQuadra;
    private Double premioQuina;
    private Double premioCheia;
    private TiraTeimaEnum TiraTeima; // [AUTOMATICO,MANUAL];
    private String tiraTeimaId;
    private String descricao;
    private String status; // ENCERRADO/ATIVO/EXCLUIDO
}
