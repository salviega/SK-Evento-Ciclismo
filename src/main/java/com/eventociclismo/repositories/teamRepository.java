package com.eventociclismo.repositories;

import com.eventociclismo.collections.Team;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface teamRepository extends ReactiveMongoRepository<Team, String> {
}
