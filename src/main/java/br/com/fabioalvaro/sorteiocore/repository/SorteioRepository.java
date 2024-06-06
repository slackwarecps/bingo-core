package br.com.fabioalvaro.sorteiocore.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import br.com.fabioalvaro.sorteiocore.dominio.Sorteio;

public interface SorteioRepository extends MongoRepository<Sorteio, String> {
}