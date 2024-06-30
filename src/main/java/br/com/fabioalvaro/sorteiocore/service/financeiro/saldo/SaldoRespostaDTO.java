package br.com.fabioalvaro.sorteiocore.service.financeiro.saldo;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class SaldoRespostaDTO {
    private String jogadorId;
    private Double saldo;
    private LocalDateTime dataAtualizada;

}