package br.com.fabioalvaro.sorteiocore.mapper;

import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import br.com.fabioalvaro.sorteiocore.model.Sorteio;
import br.com.fabioalvaro.sorteiocore.model.dto.request.SorteioDTO;
import br.com.fabioalvaro.sorteiocore.model.dto.response.SorteioMinimoDTO;
import br.com.fabioalvaro.sorteiocore.model.dto.response.SorteioNotificadosDTO;
import br.com.fabioalvaro.sorteiocore.model.dto.response.SorteioResponseDTO;

@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true))
public interface SorteioMapper {

    SorteioMapper INSTANCE = Mappers.getMapper(SorteioMapper.class);

    SorteioResponseDTO sorteioResponseDTO(Sorteio sorteio);


    @Mapping(target = "nome", source = "sorteioDTO.nome")
    @Mapping(target = "local", source = "sorteioDTO.local")
    Sorteio dtoParaSorteio(SorteioDTO sorteioDTO);


    SorteioMinimoDTO sorteioToMinimoDTO(Sorteio sorteio);

    SorteioNotificadosDTO sorteioToNotificadosDTO(Sorteio sorteio);

}
