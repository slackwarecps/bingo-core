package br.com.fabioalvaro.sorteiocore.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import br.com.fabioalvaro.sorteiocore.model.Notificacao;

public interface NotificacaoRepository extends MongoRepository<Notificacao, String> {
    List<Notificacao> findByJogadorId(String jogadorId);
}