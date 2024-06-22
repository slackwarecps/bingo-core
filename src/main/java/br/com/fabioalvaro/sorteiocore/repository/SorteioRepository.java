package br.com.fabioalvaro.sorteiocore.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import br.com.fabioalvaro.sorteiocore.model.Sorteio;

public interface SorteioRepository extends MongoRepository<Sorteio, String> {
}