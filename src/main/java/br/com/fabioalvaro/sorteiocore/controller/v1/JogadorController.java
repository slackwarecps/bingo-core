package br.com.fabioalvaro.sorteiocore.controller.v1;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.fabioalvaro.sorteiocore.model.Jogador;
import br.com.fabioalvaro.sorteiocore.service.JogadorService;

@RestController
@RequestMapping("${bingo.urlPrefixo}/jogador")
public class JogadorController {

    @Autowired
    private JogadorService jogadorService;

    @GetMapping
    public List<Jogador> getAllJogadores() {
        return jogadorService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Jogador> getJogadorById(@PathVariable String id) {
        Optional<Jogador> jogador = jogadorService.findById(id);
        if (jogador.isPresent()) {
            return ResponseEntity.ok(jogador.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public Jogador createJogador(@RequestBody Jogador jogador) {
        return jogadorService.save(jogador);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Jogador> updateJogador(@PathVariable String id, @RequestBody Jogador jogadorDetails) {
        Optional<Jogador> jogador = jogadorService.findById(id);
        if (jogador.isPresent()) {
            Jogador jogadorToUpdate = jogador.get();
            jogadorToUpdate.setNome(jogadorDetails.getNome());
            jogadorToUpdate.setSaldo(jogadorDetails.getSaldo());
            return ResponseEntity.ok(jogadorService.save(jogadorToUpdate));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteJogador(@PathVariable String id) {
        jogadorService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}