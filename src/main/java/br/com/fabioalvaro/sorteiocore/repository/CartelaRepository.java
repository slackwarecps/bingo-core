package br.com.fabioalvaro.sorteiocore.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import br.com.fabioalvaro.sorteiocore.dominio.Cartela;

public interface CartelaRepository extends MongoRepository<Cartela, String> {
}