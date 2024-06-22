package br.com.fabioalvaro.sorteiocore;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.fabioalvaro.sorteiocore.dominio.carros.Car;
import br.com.fabioalvaro.sorteiocore.dominio.carros.CarDto;
import br.com.fabioalvaro.sorteiocore.dominio.carros.CarMapper;
import br.com.fabioalvaro.sorteiocore.dominio.carros.CarType;

@SpringBootTest
public class CarMapperTest {

    @Test
    public void shouldMapCarToDto() {
        // given
        Car car = new Car("Morris", 5, CarType.SEDAN);

        // when
        CarDto carDto = CarMapper.INSTANCE.carToCarDto(car);

        // then
        assertNotNull(carDto);
        assertThat(carDto.getMake()).isEqualTo("Morris");
        assertThat(carDto.getSeatCount()).isEqualTo(5);
        assertThat(carDto.getType()).isEqualTo("SEDAN");
    }
}
