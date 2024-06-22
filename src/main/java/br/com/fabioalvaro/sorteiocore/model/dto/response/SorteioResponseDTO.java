package br.com.fabioalvaro.sorteiocore.model.dto.response;

import java.time.LocalDateTime;

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
}
