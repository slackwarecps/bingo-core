package br.com.fabioalvaro.sorteiocore.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.fabioalvaro.sorteiocore.dominio.Cartela;
import br.com.fabioalvaro.sorteiocore.dominio.Notificacao;
import br.com.fabioalvaro.sorteiocore.repository.NotificacaoRepository;

@Service
public class NotificacaoService {

    @Autowired
    private NotificacaoRepository notificacaoRepository;

    public List<Notificacao> findByJogadorId(String jogadorId) {
        return notificacaoRepository.findByJogadorId(jogadorId);
    }

    public Notificacao save(Notificacao notificacao) {
        LocalDateTime created = LocalDateTime.now();
        notificacao.setCreatedAt(created);
        notificacao.setVisualizado(false);
        return notificacaoRepository.save(notificacao);
    }

    public Notificacao update(String id, Notificacao updatedNotificacao) {
        Optional<Notificacao> optionalNotificacao = notificacaoRepository.findById(id);
        if (optionalNotificacao.isPresent()) {
            Notificacao existingNotificacao = optionalNotificacao.get();

            existingNotificacao.setVisualizado(updatedNotificacao.getVisualizado());
            // Atualize outros campos conforme necessário
            return notificacaoRepository.save(existingNotificacao);
        } else {
            throw new RuntimeException("Notificação não encontrada com o ID: " + id);
        }
    }

    public void notificaCartela(Cartela cartela, String mensagem) {
        Notificacao notificacao = new Notificacao();
        notificacao.setJogadorId(cartela.getJogadorId());
        notificacao.setMensagem(mensagem);
        this.save(notificacao);
        // throw new UnsupportedOperationException("Unimplemented method
        // 'notificaCartela'");
    }

}