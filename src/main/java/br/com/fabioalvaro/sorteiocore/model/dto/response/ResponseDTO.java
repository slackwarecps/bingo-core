package br.com.fabioalvaro.sorteiocore.model.dto.response;

import java.time.LocalDateTime;

public class ResponseDTO {
    private String id;
    private LocalDateTime criado;
    private String jogador;

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