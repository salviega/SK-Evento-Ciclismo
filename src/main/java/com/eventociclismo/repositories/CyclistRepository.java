package com.eventociclismo.repositories;

import com.eventociclismo.collections.Cyclist;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface CyclistRepository extends ReactiveCrudRepository<Cyclist, String> {
    Flux<Cyclist> findAllByTeamId(String id);
    Mono<Void> deleteByTeamId(String teamId);

}
