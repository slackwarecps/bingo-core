package br.com.fabioalvaro.sorteiocore.service.financeiro.saldo;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "saldo", url = "https://open-bingo.wiremockapi.cloud/")
public interface SaldoClient {

    @GetMapping("/saldo/{jogadorId}")
    SaldoRespostaDTO getSaldoByJogadorId(@PathVariable("jogadorId") String jogadorId);

}
