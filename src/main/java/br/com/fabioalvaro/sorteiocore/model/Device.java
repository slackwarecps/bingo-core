package br.com.fabioalvaro.sorteiocore.model;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
@Document(collection = "device")
public class Device {
    @Id
    private String id;
    private String token;
    private String modelo;
    private String marca;


}