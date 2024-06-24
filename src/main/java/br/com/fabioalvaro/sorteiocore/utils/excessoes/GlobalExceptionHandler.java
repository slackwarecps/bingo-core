package br.com.fabioalvaro.sorteiocore.utils.excessoes;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import br.com.fabioalvaro.sorteiocore.utils.excessoes.ErroDeDominio.ErroClientSaldoException;
import br.com.fabioalvaro.sorteiocore.utils.excessoes.ErroDeDominio.FullException;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ErroClientSaldoException.class)
    public ResponseEntity<String> handleClientSaldoException(ErroClientSaldoException ex) {
        String errorMessage = "Erro na requisição do Cliente";
        // Optionally log the exception here
        // logger.error("ErroClientSaldoException occurred: ", ex);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
    }

    @ExceptionHandler(FullException.class)
    public ResponseEntity<String> eita(FullException ex) {
        String errorMessage = "FULL Erro na requisição do Cliente";
        // Optionally log the exception here
        // logger.error("ErroClientSaldoException occurred: ", ex);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<MensagemDeErro> eita(NullPointerException ex) {
        // String errorMessage = "NullPointerException Erro na requisição do Cliente";
        MensagemDeErro mensagemDeErro = new MensagemDeErro(HttpStatus.NOT_FOUND, ex.getMessage());
        // Optionally log the exception here
        // logger.error("ErroClientSaldoException occurred: ", ex);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(mensagemDeErro);
    }

}
