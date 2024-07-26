package br.com.fabioalvaro.sorteiocore.mapper;

import java.util.List;

import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import br.com.fabioalvaro.sorteiocore.model.Event;

import br.com.fabioalvaro.sorteiocore.model.dto.response.EventResponseDTO;

@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true))
public interface EventMapper {

    EventMapper INSTANCE = Mappers.getMapper(EventMapper.class);

    @Mapping(target = "imagem", source = "event.image")
    @Mapping(target = "titulo", source = "event.title")
    List<EventResponseDTO> mapToDtoList(List<Event> events);

    @Mapping(target = "id", source = "event.id")
    @Mapping(target = "inscritos", source = "event.inscriptions")
    @Mapping(target = "titulo", source = "event.title")
    @Mapping(target = "descricao", source = "event.description")
    @Mapping(target = "imagem", source = "event.image")
    EventResponseDTO eventToEventResponseDTO(Event event);
}
