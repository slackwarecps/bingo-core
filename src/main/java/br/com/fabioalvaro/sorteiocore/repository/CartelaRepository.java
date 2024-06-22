package br.com.fabioalvaro.sorteiocore.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import br.com.fabioalvaro.sorteiocore.model.Cartela;

public interface CartelaRepository extends MongoRepository<Cartela, String> {

    List<Cartela> findBySorteioId(String sorteioId);

    Optional<Cartela> findById(String id);

    List<Cartela> findBySorteioIdAndGanhouQuadra(String sorteioId, boolean ganhouQuadra);

}