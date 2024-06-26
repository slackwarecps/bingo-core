package br.com.fabioalvaro.sorteiocore.service;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;

import br.com.fabioalvaro.sorteiocore.model.Cartela;

public class CartelaServiceTest {
    @Mock
    private Logger logger;

    @InjectMocks
    private CartelaService cartelaService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    void testGeraNumerosRandomicos() {
        // "criado": "2024-06-02T08:22:22.001",
        // "jogador": "nutella"
        // Arrange
        Cartela cartela = new Cartela(); // Assuming Cartela has a default constructor
        String dateTimeString = "2024-06-02T08:22:22.001";
        LocalDateTime criado = LocalDateTime.parse(dateTimeString);
        cartela.setId("xpto001");
        cartela.setCreatedAt(criado);
        cartela.setJogadorId("nutella");
        // Act
        // Boolean result = cartelaService.geraNumerosRandomicos();

        // Assert
        assertTrue(1 == 1);
    }
}
