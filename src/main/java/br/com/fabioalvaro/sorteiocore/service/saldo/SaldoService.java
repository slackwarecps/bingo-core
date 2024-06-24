package br.com.fabioalvaro.sorteiocore.service.saldo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SaldoService {

    @Autowired
    private SaldoClient client;

    public Double getSaldoDoJogador(String jogadorId) {

        Double retorno = 0.00;

        SaldoRespostaDTO saldo = client.getSaldoByJogadorId(jogadorId);
        retorno = saldo.getSaldo();
        return retorno;
    }

}
