package br.com.fabioalvaro.sorteiocore.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("${bingo.urlPrefixo}")
public class HealthController {




    @GetMapping("/health")
    public ResponseEntity<String> healthPadrao() {
        String resposta = "sorteio-core: OK";
        return ResponseEntity.ok(resposta);
    }
}
