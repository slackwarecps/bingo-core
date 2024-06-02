package br.com.fabioalvaro.sorteiocore.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/padrao")
public class HealthController {
    @GetMapping("/health")
    public ResponseEntity<String> healthPadrao() {

        String resposta = "OK Padrao health!!!";
        return ResponseEntity.ok(resposta);
    }
}
