package br.com.fabioalvaro.sorteiocore.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import br.com.fabioalvaro.sorteiocore.model.MovimentoFinanceiro;

@Repository
public interface MovimentoFinanceiroRepository extends MongoRepository<MovimentoFinanceiro, String> {
    // Aqui você pode adicionar consultas personalizadas, se necessário
    List<MovimentoFinanceiro> findByOrigem(String jogadorId);
}