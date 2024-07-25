package br.com.fabioalvaro.sorteiocore.model.dto.response;



import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EventResponseDTO {
    private String id;
    private int inscritos;
    private String titulo;
    private String descricao;
    private String imagem;
  
}
