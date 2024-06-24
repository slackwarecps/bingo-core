package br.com.fabioalvaro.sorteiocore.service.saldo;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "saldo", url = "https://open-bingo.wiremockapi.cloud/")
public interface SaldoClient {

    @GetMapping("/saldo")
    SaldoRespostaDTO getSaldoByJogadorId(@RequestParam("jogadorId") String jogadorId);

}
