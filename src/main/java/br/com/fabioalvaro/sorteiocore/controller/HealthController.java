package br.com.fabioalvaro.sorteiocore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.fabioalvaro.sorteiocore.utils.VersionReader;

@RestController
@RequestMapping("/sorteio-core/v1")
public class HealthController {


        @Autowired
    private VersionReader versionReader;


    @GetMapping("/health")
    public ResponseEntity<String> healthPadrao() {
        String resposta = "sorteio-core: OK - ";
        String version = versionReader.getVersion();
        resposta = resposta + version;
        return ResponseEntity.ok(resposta);
    }
}
