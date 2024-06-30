package br.com.fabioalvaro.sorteiocore.controller;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.fabioalvaro.sorteiocore.model.Linha;
import br.com.fabioalvaro.sorteiocore.model.Sorteio;
import br.com.fabioalvaro.sorteiocore.service.sorteio.SorteioService;

@RestController
@RequestMapping("/teste")
public class TesteController {
    private static final Logger logger = LoggerFactory.getLogger(SorteioService.class);

    @Autowired
    private SorteioService sorteioService;

    @GetMapping(path = "/linha", produces = "application/json")
    public String getMethodName() {

        Sorteio sorteio = new Sorteio();
        sorteio.setLista_numeros_sorteados(Arrays.asList(1, 2, 3, 4, 5));

        Linha linha = new Linha();
        linha.setLinha(Arrays.asList(1, 2, 3, 25, 35));

        // Boolean result = sorteioService.linhaganhou(sorteio, linha, "QUADRA");
        // logger.info("RETORNO: {} ", result);

        return "opa";
    }

}
