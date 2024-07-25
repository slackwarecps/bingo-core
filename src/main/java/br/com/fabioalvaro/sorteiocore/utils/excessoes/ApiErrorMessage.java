package br.com.fabioalvaro.sorteiocore.utils.excessoes;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Builder;
import lombok.Data;


@Builder
@Data
public class ApiErrorMessage {
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime timestamp;

    private Integer code;
    private String status;
    private List<String> errors;

    // public ApiErrorMessage(HttpStatus status, List<String> errors) {
    //     super();
    //     this.status = status;
    //     this.errors = errors;
    // }

    // public ApiErrorMessage(HttpStatus status, String error) {
    //     super();
    //     this.status = status;
    //     errors = Arrays.asList(error);
    // }
}
