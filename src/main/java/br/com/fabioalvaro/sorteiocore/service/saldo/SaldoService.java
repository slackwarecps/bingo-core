package br.com.fabioalvaro.sorteiocore.service.saldo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import br.com.fabioalvaro.sorteiocore.controller.v1.CartelaController;

@Service
public class SaldoService {
    private static final Logger logger = LoggerFactory.getLogger(CartelaController.class);
    private String JOGADOR_ID = "666eba2b6ec8517e8b884818";
    @Autowired
    private SaldoClient client;

    public Double getSaldoDoJogador(String jogadorId) {

        Double retorno = 0.00;

        // @TODO Precisa depois remover o hardcoded
        SaldoRespostaDTO saldo = client.getSaldoByJogadorId(JOGADOR_ID);

        retorno = saldo.getSaldo();
        return retorno;

    }

}
