package br.com.fabioalvaro.sorteiocore.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import br.com.fabioalvaro.sorteiocore.model.Device;

public interface DeviceRepository extends MongoRepository<Device, String> {
}