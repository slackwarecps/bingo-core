package br.com.fabioalvaro.sorteiocore.model.dto.response;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class SorteioMinimoDTO {
    private String id;
    private String local;
    private String nome;
    private LocalDateTime createAt;
    private LocalDateTime updatedAt;
    private String status;
}
