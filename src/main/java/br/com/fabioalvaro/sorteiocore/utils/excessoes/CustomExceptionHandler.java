package br.com.fabioalvaro.sorteiocore.utils.excessoes;



import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import br.com.fabioalvaro.sorteiocore.utils.excessoes.ErroDeDominio.JogadorNaoExisteException;
import br.com.fabioalvaro.sorteiocore.utils.excessoes.ErroDeDominio.SorteioNaoExisteException;
import br.com.fabioalvaro.sorteiocore.utils.excessoes.ErroDeDominio.UserNotFoundException;
import br.com.fabioalvaro.sorteiocore.utils.excessoes.ErroDeDominio.VendedorNaoExisteException;




@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

   


    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorMessage> genericException(Exception ex) {
        ApiErrorMessage apiError = ApiErrorMessage
                .builder()
                .timestamp(LocalDateTime.now())
                .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .status(HttpStatus.INTERNAL_SERVER_ERROR.name())
                .errors(List.of(ex.getMessage()))
                .build();
        return new ResponseEntity<>(apiError, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @ExceptionHandler(SorteioNaoExisteException.class)
    public ResponseEntity<ApiErrorMessage> handleSorteioException(SorteioNaoExisteException ex) {        
        ApiErrorMessage apiError = ApiErrorMessage
                .builder()
                .timestamp(LocalDateTime.now())
                .code(HttpStatus.BAD_REQUEST.value())
                .status(HttpStatus.BAD_REQUEST.name())
                .errors(List.of(ex.getMessage()))
                .build();
        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(JogadorNaoExisteException.class)
    public ResponseEntity<MensagemDeErro> handleJogadorNaoexisteException(JogadorNaoExisteException ex) {        
        MensagemDeErro mensagemDeErro = new MensagemDeErro(HttpStatus.NOT_FOUND, ex.getMessage());
        logger.error("PEGOU O ERRO!!!!",ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(mensagemDeErro);
    }

    @ExceptionHandler(VendedorNaoExisteException.class)
    public ResponseEntity<MensagemDeErro> handleVendedorNaoExisteException(VendedorNaoExisteException ex) {        
        MensagemDeErro mensagemDeErro = new MensagemDeErro(HttpStatus.NOT_FOUND, ex.getMessage());
        logger.error("PEGOU O ERRO!!!!",ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(mensagemDeErro);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Object> handleUserNotFoundException(
            UserNotFoundException exception, WebRequest request) {

                ApiErrorMessage apiError = ApiErrorMessage
                .builder()
                .timestamp(LocalDateTime.now())
                .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .status(HttpStatus.INTERNAL_SERVER_ERROR.name())
                .errors(List.of(exception.getMessage()))
                .build();

        return new ResponseEntity<>(apiError, HttpStatus.INTERNAL_SERVER_ERROR);
    }



    
    // public ResponseEntity<ApiErrorMessage> handleIxiManException(MethodArgumentNotValidException ex) {
    //     List<String> errors = ex.getBindingResult().getFieldErrors().stream()
    //             .map(error -> error.getDefaultMessage())
    //             .collect(Collectors.toList());

    //     ApiErrorMessage apiError = ApiErrorMessage
    //             .builder()
    //             .timestamp(LocalDateTime.now())
    //             .code(HttpStatus.BAD_REQUEST.value())
    //             .status(HttpStatus.BAD_REQUEST.name())
    //             .errors(errors)
    //             .build();

    //     return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    // }

}
