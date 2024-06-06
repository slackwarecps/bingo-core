package br.com.fabioalvaro.sorteiocore.dominio.dto.request;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;

public class CartelaDTO {
    private String id;
    private LocalDateTime criado;
    private String jogador;
    @NotBlank
    private String sorteioId;

    public String getSorteioId() {
        return sorteioId;
    }

    public void setSorteioId(String sorteioId) {
        this.sorteioId = sorteioId;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDateTime getCriado() {
        return criado;
    }

    public void setCriado(LocalDateTime criado) {
        this.criado = criado;
    }

    public String getJogador() {
        return jogador;
    }

    public void setJogador(String jogador) {
        this.jogador = jogador;
    }
}
