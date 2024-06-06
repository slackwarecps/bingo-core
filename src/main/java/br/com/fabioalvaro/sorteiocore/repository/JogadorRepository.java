package br.com.fabioalvaro.sorteiocore.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import br.com.fabioalvaro.sorteiocore.dominio.Jogador;

public interface JogadorRepository extends MongoRepository<Jogador, String> {
}