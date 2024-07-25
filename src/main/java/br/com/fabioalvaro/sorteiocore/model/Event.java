package br.com.fabioalvaro.sorteiocore.model;

import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.persistence.Id;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@Document(collection = "event")
public class Event {
    @Id
    private String id;
    private String title;
    private String description;
    private String image;
    private int inscriptions;


}