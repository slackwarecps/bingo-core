package br.com.fabioalvaro.sorteiocore.controller;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.fabioalvaro.sorteiocore.model.Linha;
import br.com.fabioalvaro.sorteiocore.model.Sorteio;
import br.com.fabioalvaro.sorteiocore.model.dto.request.CartelaDTO;
import br.com.fabioalvaro.sorteiocore.service.SorteioService;
import br.com.fabioalvaro.sorteiocore.utils.excessoes.ErroDeDominio.ErroClientSaldoException;
import br.com.fabioalvaro.sorteiocore.utils.excessoes.ErroDeDominio.SorteioNaoExisteException;
import br.com.fabioalvaro.sorteiocore.utils.excessoes.ErroDeDominio.UserNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("${bingo.urlPrefixo}/teste")
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

    @GetMapping(path = "/erro1/{errorCode}", produces = "application/json")
    public String callErro1(@PathVariable int errorCode) throws Exception {
        switch (errorCode) {
            case 1:
                throw new ErroClientSaldoException("Erro ao chamar a api de saldo!");
            case 2:
                throw new Exception("Algo não deu bom aqui meu querido!!!");
                case 3:
                 throw new UserNotFoundException(123L);
                 case 4:
                 throw new SorteioNaoExisteException();
                 
            default:
                throw new Exception("Código de erro inválido!");
        }
    }



    @PostMapping(path = "/erro2", produces = "application/json")
    public String criaCartelaTeste(CartelaDTO cartelaDto) throws Exception {
        return "ok";
    }

}
