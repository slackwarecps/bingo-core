package br.com.fabioalvaro.sorteiocore.service.saldo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.fabioalvaro.sorteiocore.controller.CartelaController;

@Service
public class SaldoService {
    private static final Logger logger = LoggerFactory.getLogger(CartelaController.class);

    @Autowired
    private SaldoClient client;

    public Double getSaldoDoJogador(String jogadorId) {

        Double retorno = 0.00;

        SaldoRespostaDTO saldo = client.getSaldoByJogadorId(jogadorId);

        retorno = saldo.getSaldo();
        return retorno;

    }

}
