package br.com.fabioalvaro.sorteiocore.model;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
@Document(collection = "notificacao")
public class Notificacao {
    @Id
    String id;
    @NotNull
    LocalDateTime createdAt;
    @NotNull
    String mensagem;
    @NotNull
    String jogadorId;
    Boolean visualizado;
}