package com.eventociclismo.repositories;

import com.eventociclismo.collections.Cyclist;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface cyclistRepository extends ReactiveCrudRepository<Cyclist, String> {

}
