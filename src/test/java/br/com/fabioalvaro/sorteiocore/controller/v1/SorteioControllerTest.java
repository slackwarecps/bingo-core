package br.com.fabioalvaro.sorteiocore.controller.v1;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import br.com.fabioalvaro.sorteiocore.model.Sorteio;
import br.com.fabioalvaro.sorteiocore.service.SorteioService;

public class SorteioControllerTest {
     @Mock
    private SorteioService sorteioService;

    @InjectMocks
    private SorteioController sorteioController;


    public SorteioControllerTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetSorteioById() {

    }

  @Test
    void testGetSorteioById_SorteioEncontrado() {
        // Arrange
        String id = "123";
        Sorteio sorteioMock = new Sorteio();
        sorteioMock.setId(id);
        sorteioMock.setNome("Sorteio Teste");
        when(sorteioService.buscarSorteioPorId(id)).thenReturn(Optional.of(sorteioMock));

        // Act
        ResponseEntity<Sorteio> response = sorteioController.getSorteioById(id);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(sorteioMock, response.getBody());
    }

    @Test
    void testGetSorteioById_SorteioNaoEncontrado() {
        // Arrange
        String id = "123";
        when(sorteioService.buscarSorteioPorId(id)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<Sorteio> response = sorteioController.getSorteioById(id);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(null, response.getBody());
    }


}
