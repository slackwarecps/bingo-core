package br.com.fabioalvaro.sorteiocore.dominio;

import java.util.List;

public class Linha {
    private boolean ganhouQuadra;
    private boolean ganhouQuina;
    private List<Integer> linha;

    public boolean isGanhouQuadra() {
        return ganhouQuadra;
    }

    public void setGanhouQuadra(boolean ganhouQuadra) {
        this.ganhouQuadra = ganhouQuadra;
    }

    public boolean isGanhouQuina() {
        return ganhouQuina;
    }

    public void setGanhouQuina(boolean ganhouQuina) {
        this.ganhouQuina = ganhouQuina;
    }

    public List<Integer> getLinha() {
        return linha;
    }

    public void setLinha(List<Integer> linha) {
        this.linha = linha;
    }

    @Override
    public String toString() {
        return "Linha [ganhouQuadra=" + ganhouQuadra + ", ganhouQuina=" + ganhouQuina + ", linha=" + linha + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (ganhouQuadra ? 1231 : 1237);
        result = prime * result + (ganhouQuina ? 1231 : 1237);
        result = prime * result + ((linha == null) ? 0 : linha.hashCode());
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
        Linha other = (Linha) obj;
        if (ganhouQuadra != other.ganhouQuadra)
            return false;
        if (ganhouQuina != other.ganhouQuina)
            return false;
        if (linha == null) {
            if (other.linha != null)
                return false;
        } else if (!linha.equals(other.linha))
            return false;
        return true;
    }

}