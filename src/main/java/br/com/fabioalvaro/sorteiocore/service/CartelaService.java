package br.com.fabioalvaro.sorteiocore.service;

import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.fabioalvaro.sorteiocore.config.ParametrosConfig;
import br.com.fabioalvaro.sorteiocore.model.Cartela;
import br.com.fabioalvaro.sorteiocore.model.RandomNumbers;
import br.com.fabioalvaro.sorteiocore.model.Sorteio;
import br.com.fabioalvaro.sorteiocore.repository.CartelaRepository;
import br.com.fabioalvaro.sorteiocore.repository.SorteioRepository;

@Service
public class CartelaService {

    private static final Logger logger = LoggerFactory.getLogger(CartelaService.class);

    @Autowired
    private CartelaRepository cartelaRepository;

    @Autowired
    private SorteioRepository sorteioRepository;

    @Autowired
    private MovimentoFinanceiroService movimentoFinanceiroService;

    @Autowired
    private NotificacaoService notificacaoService;

    @Autowired
    private RandomNumbers randomNumbers;

    private final ParametrosConfig parametrosConfig;

    public CartelaService(ParametrosConfig parametrosConfig) {
        this.parametrosConfig = parametrosConfig;
    }

    public Cartela adicionarCartela(Cartela cartela) {

        // Adiciona Cartela ao Sorteio
        Optional<Sorteio> optionalSorteio = sorteioRepository.findById(cartela.getSorteioId());
        if (optionalSorteio.isPresent()) {
            Sorteio sorteio = optionalSorteio.get();
            sorteio.setCartelasQtd(sorteio.getCartelasQtd() + 1);
            sorteioRepository.save(sorteio); // Salva o sorteio atualizado
        }
        cartela.setCreatedAt(LocalDateTime.now());
        cartela.setGanhouCheia(false);
        cartela.setGanhouQuadra(false);
        cartela.setGanhouQuina(false);
        cartela.setValor(cartela.getValor());
        return cartelaRepository.save(cartela);
    }

    public List<List<Integer>> geraNumerosRandomicos() {
        List<Integer> listaInicial = List.of();
        logger.info("Gerando numeros randomicos da Cartela: {}");
        List<Integer> lista_numeros1 = randomNumbers.generateRandomNumbers(5, parametrosConfig.getNumero_minimo(),
                parametrosConfig.getNumero_maximo(),
                listaInicial);
        List<Integer> lista_numeros2 = randomNumbers.generateRandomNumbers(5, parametrosConfig.getNumero_minimo(),
                parametrosConfig.getNumero_maximo(), lista_numeros1);
        List<Integer> lista_numeros3 = randomNumbers.generateRandomNumbers(5, parametrosConfig.getNumero_minimo(),
                parametrosConfig.getNumero_maximo(), lista_numeros2);
        // logger.info("lista de numeros 1: {}", lista_numeros1);
        // logger.info("lista de numeros 2: {}", lista_numeros2);
        // logger.info("lista de numeros 3: {}", lista_numeros3);
        List<List<Integer>> conjunto = new ArrayList<>();
        conjunto.add(lista_numeros1);
        conjunto.add(lista_numeros2);
        conjunto.add(lista_numeros3);
        logger.info("Numeros Gerados: {}", conjunto);

        Boolean retorno = true;
        return conjunto;
    }

    public List<Cartela> buscarCartelaPorSorteioId(String sorteioId) {
        return cartelaRepository.findBySorteioId(sorteioId);
    }

    public List<Cartela> buscarCartelasGanhouQuadraPorSorteioId(String sorteioId) {
        return cartelaRepository.findBySorteioIdAndGanhouQuadra(sorteioId, true);
    }

    public Boolean premiarCartela(Cartela cartelaRetornada, double valor) {
        // TODO Auto-generated method stub
        Boolean retorno = movimentoFinanceiroService.adicionaCredito(cartelaRetornada, valor);

        NumberFormat nf = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
        String valorFormatado = nf.format(valor);

        // notifica ganhador
        String mensagem = "Parabens! sua Cartela " + cartelaRetornada.getId() + " foi Premiada no valor de "
                + String.valueOf(valorFormatado);
        notificacaoService.notificaCartela(cartelaRetornada, mensagem);
        return retorno;
    }

    public Optional<Cartela> buscarCartelaPorId(String id) {
        return cartelaRepository.findById(id);
    }

}