package com.eventociclismo.repositories;

import com.eventociclismo.collections.Cyclist;
import com.eventociclismo.collections.Team;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface TeamRepository extends ReactiveMongoRepository<Team, String> {
    Mono<Team> findByTeamName(String teamName);
}
