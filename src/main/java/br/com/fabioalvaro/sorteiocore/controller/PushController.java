package br.com.fabioalvaro.sorteiocore.controller;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.fabioalvaro.sorteiocore.mapper.EventMapper;
import br.com.fabioalvaro.sorteiocore.mapper.SorteioMapper;
import br.com.fabioalvaro.sorteiocore.model.Device;
import br.com.fabioalvaro.sorteiocore.model.Event;
import br.com.fabioalvaro.sorteiocore.model.dto.response.EventResponseDTO;
import br.com.fabioalvaro.sorteiocore.service.DeviceService;
import br.com.fabioalvaro.sorteiocore.service.EventService;

@RestController
@RequestMapping("${bingo.urlPrefixo}/push")
public class PushController {
    private static final Logger logger = LoggerFactory.getLogger(PushController.class);

    @Autowired
    private DeviceService deviceService;
    @Autowired
    private EventService eventService;

    @Autowired
    private EventMapper eventMapper;

    @GetMapping
    public List<Device> getAllJogadores() {
        return deviceService.findAll();
    }

    @GetMapping("/events")
    public List<EventResponseDTO> getAllEvent() {
        logger.info("Buscou /events");
        return eventService.findAll();
    }

    @GetMapping("/events/{id}")
    public ResponseEntity<EventResponseDTO> getEventById(@PathVariable String id) {
        System.out.println("Buscou /events/{id}");
        Optional<Event> event = eventService.findById(id);
        if (event.isPresent()) {
            EventResponseDTO eventResponseDTO =  eventMapper.eventToEventResponseDTO(event.get());
            return ResponseEntity.ok(eventResponseDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Device> getJogadorById(@PathVariable String id) {
        Optional<Device> jogador = deviceService.findById(id);
        if (jogador.isPresent()) {
            return ResponseEntity.ok(jogador.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/devices")
    public Device createDevice(@RequestBody Device device) {
        logger.info("criando device");
        logger.info(device.toString());
        
        return deviceService.save(device);
    }

   
}