package br.com.fabioalvaro.sorteiocore.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import br.com.fabioalvaro.sorteiocore.dominio.Vendedor;

public interface VendedorRepository extends MongoRepository<Vendedor, String> {
}