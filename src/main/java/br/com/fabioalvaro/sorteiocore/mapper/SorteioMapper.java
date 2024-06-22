package br.com.fabioalvaro.sorteiocore.mapper;

import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import br.com.fabioalvaro.sorteiocore.model.Sorteio;
import br.com.fabioalvaro.sorteiocore.model.dto.response.SorteioMinimoDTO;
import br.com.fabioalvaro.sorteiocore.model.dto.response.SorteioNotificadosDTO;
import br.com.fabioalvaro.sorteiocore.model.dto.response.SorteioResponseDTO;

@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true))
public interface SorteioMapper {

    SorteioMapper INSTANCE = Mappers.getMapper(SorteioMapper.class);

    SorteioResponseDTO sorteioResponseDTO(Sorteio sorteio);

    SorteioMinimoDTO sorteioToMinimoDTO(Sorteio sorteio);

    SorteioNotificadosDTO sorteioToNotificadosDTO(Sorteio sorteio);

}
