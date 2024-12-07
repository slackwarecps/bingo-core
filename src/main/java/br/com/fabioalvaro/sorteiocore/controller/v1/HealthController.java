package br.com.fabioalvaro.sorteiocore.controller.v1;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("${bingo.urlPrefixo}")
public class HealthController {

    @GetMapping("/health")
    public ResponseEntity<Map<String, String>> healthPadrao() {
        Map<String, String> resposta = new HashMap<>();
        resposta.put("status", "OK");
        resposta.put("data", "22/07/2024 1422");
        resposta.put("message", "depois do almoço");
        return ResponseEntity.ok(resposta);
    }
}
