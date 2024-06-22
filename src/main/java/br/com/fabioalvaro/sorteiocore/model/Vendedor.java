package br.com.fabioalvaro.sorteiocore.model;

import java.time.LocalDateTime;

import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
@Document(collection = "vendedor")
public class Vendedor {

    @Id
    private String id;

    private String nome;

    private Double saldoVendido;
    private LocalDateTime createdAt;

}