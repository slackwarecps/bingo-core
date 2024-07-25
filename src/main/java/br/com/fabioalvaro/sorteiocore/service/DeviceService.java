package br.com.fabioalvaro.sorteiocore.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.fabioalvaro.sorteiocore.model.Device;
import br.com.fabioalvaro.sorteiocore.repository.DeviceRepository;
import br.com.fabioalvaro.sorteiocore.repository.JogadorRepository;

@Service
public class DeviceService {

    @Autowired
    private DeviceRepository deviceRepository;

    public List<Device> findAll() {
        return deviceRepository.findAll();
    }

    public Optional<Device> findById(String id) {
        return deviceRepository.findById(id);
    }

    public Optional<Device> buscarJogadorById(String id) {
        return deviceRepository.findById(id);
    }

    public Device save(Device jogador) {
        
        return deviceRepository.save(jogador);
    }

    public void deleteById(String id) {
        deviceRepository.deleteById(id);
    }
}