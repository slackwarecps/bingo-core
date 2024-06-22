package br.com.fabioalvaro.sorteiocore.dominio.dto.response;

import java.util.List;

import br.com.fabioalvaro.sorteiocore.dominio.Sorteio;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SorteioNotificadosDTO {
    String sorteioId;
    String local;
    Sorteio sorteio;
    List<CartelaNotificadosDTO> listaDeCartelas;
}
