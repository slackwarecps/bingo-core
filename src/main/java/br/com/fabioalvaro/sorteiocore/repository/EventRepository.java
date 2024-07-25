package br.com.fabioalvaro.sorteiocore.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import br.com.fabioalvaro.sorteiocore.model.Device;
import br.com.fabioalvaro.sorteiocore.model.Event;

public interface EventRepository extends MongoRepository<Event, String> {
}