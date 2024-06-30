package br.com.fabioalvaro.sorteiocore.controller.jogo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.com.fabioalvaro.sorteiocore.model.jogo.Notificacao;
import br.com.fabioalvaro.sorteiocore.service.NotificacaoService;

@RestController
@RequestMapping("/notificacao")
public class NotificacaoController {

    @Autowired
    private NotificacaoService notificacaoService;

    @GetMapping("{jogadorId}")
    public List<Notificacao> getNotificacoesByJogadorId(@PathVariable String jogadorId) {
        return notificacaoService.findByJogadorId(jogadorId);
    }

    @PostMapping
    public Notificacao createNotificacao(@RequestBody Notificacao notificacao) {
        return notificacaoService.save(notificacao);
    }

    @PutMapping("/{id}")
    public Notificacao updateNotificacao(@PathVariable String id, @RequestBody Notificacao updatedNotificacao) {
        Notificacao existingNotificacao = notificacaoService.findById(id);
        if (existingNotificacao == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Notificação não encontrada para o ID: " + id);
        }
        return notificacaoService.update(id, updatedNotificacao);
    }
}