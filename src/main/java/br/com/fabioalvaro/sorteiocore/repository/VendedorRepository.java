package br.com.fabioalvaro.sorteiocore.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import br.com.fabioalvaro.sorteiocore.model.Vendedor;

public interface VendedorRepository extends MongoRepository<Vendedor, String> {
}