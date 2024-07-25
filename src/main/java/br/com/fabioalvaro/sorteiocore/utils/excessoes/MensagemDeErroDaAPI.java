package br.com.fabioalvaro.sorteiocore.utils.excessoes;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpStatus;

import lombok.Data;

@Data
public class MensagemDeErroDaAPI {
    private HttpStatus status;
    private List<String> erros;

    /**
     * Constructs a new instance of the MensagemDeErroDaAPI class.
     * 
     * @param status The HTTP status code associated with the error.
     * @param erros  The list of error messages.
     */
    public MensagemDeErroDaAPI(HttpStatus status, List<String> erros) {
        this.status = status;
        this.erros = erros;
    }

    public MensagemDeErroDaAPI(HttpStatus status, String erro) {
        this.status = status;
        this.erros = Arrays.asList(erro);
    }

}
