package br.com.fabioalvaro.sorteiocore.dominio.enums;

public enum TipoSorteioEnum {
    FIXO(0),
    AUTOMATICO(1);

    Integer tipo;

    private TipoSorteioEnum(Integer tipo) {
        this.tipo = tipo;
    }

    public Integer getTipo() {
        return tipo;
    }

    public static TipoSorteioEnum getPorTipo(Integer tipo) {
        TipoSorteioEnum retorno = null;

        for (TipoSorteioEnum modelo : TipoSorteioEnum.values()) {
            if (modelo.getTipo().equals(tipo)) {
                retorno = modelo;
                break;
            }
        }
        return retorno;
    }
}