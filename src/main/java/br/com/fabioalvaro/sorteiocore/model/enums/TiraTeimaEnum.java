package br.com.fabioalvaro.sorteiocore.model.enums;

public enum TiraTeimaEnum {

    MANUAL(0),
    AUTOMATICO(1);

    Integer tipo;

    private TiraTeimaEnum(Integer tipo) {
        this.tipo = tipo;
    }

    public Integer getTipo() {
        return tipo;
    }

    public static TiraTeimaEnum getPorTipo(Integer tipo) {
        TiraTeimaEnum retorno = null;

        for (TiraTeimaEnum modelo : TiraTeimaEnum.values()) {
            if (modelo.getTipo().equals(tipo)) {
                retorno = modelo;
                break;
            }
        }
        return retorno;
    }

}
