package br.com.fabioalvaro.sorteiocore.service;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.fabioalvaro.sorteiocore.mapper.EventMapper;
import br.com.fabioalvaro.sorteiocore.mapper.SorteioMapper;
import br.com.fabioalvaro.sorteiocore.model.Event;
import br.com.fabioalvaro.sorteiocore.model.dto.response.EventResponseDTO;
import br.com.fabioalvaro.sorteiocore.repository.EventRepository;


@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private EventMapper eventMapper;

    public List<EventResponseDTO> findAll() {
        List<Event> events = eventRepository.findAll();
        return eventMapper.mapToDtoList(events);
    }

    public Optional<Event> findById(String id) {
        return eventRepository.findById(id);
    }

    public Optional<Event> buscarEventById(String id) {
        return eventRepository.findById(id);
    }

    public Event save(Event event) {
        
        return eventRepository.save(event);
    }

    public void deleteById(String id) {
        eventRepository.deleteById(id);
    }
}