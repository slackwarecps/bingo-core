package br.com.fabioalvaro.sorteiocore.model.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class CartelaDTO {
    @NotNull(message = "jogadorId é obrigatorio.")
    private String jogadorId;
    @NotBlank(message = "sorteioId é obrigatorio.")
    private String sorteioId;
    @NotBlank(message = "vendedorId é obrigatorio.")
    private String vendedorId;

    private Double valor;

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public String getVendedorId() {
        return vendedorId;
    }

    public void setVendedorId(String vendedorId) {
        this.vendedorId = vendedorId;
    }

    public String getSorteioId() {
        return sorteioId;
    }

    public void setSorteioId(String sorteioId) {
        this.sorteioId = sorteioId;
    }

    public String getJogadorId() {
        return jogadorId;
    }

    public void setJogadorId(String jogador) {
        this.jogadorId = jogador;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((jogadorId == null) ? 0 : jogadorId.hashCode());
        result = prime * result + ((sorteioId == null) ? 0 : sorteioId.hashCode());
        result = prime * result + ((vendedorId == null) ? 0 : vendedorId.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        CartelaDTO other = (CartelaDTO) obj;

        if (jogadorId == null) {
            if (other.jogadorId != null)
                return false;
        } else if (!jogadorId.equals(other.jogadorId))
            return false;
        if (sorteioId == null) {
            if (other.sorteioId != null)
                return false;
        } else if (!sorteioId.equals(other.sorteioId))
            return false;
        if (vendedorId == null) {
            if (other.vendedorId != null)
                return false;
        } else if (!vendedorId.equals(other.vendedorId))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "CartelaDTO [jogadorId=" + jogadorId + ", sorteioId=" + sorteioId + ", vendedorId=" + vendedorId + "]";
    }

}
