package com.eventociclismo.use_case;

import com.eventociclismo.repositories.CyclistRepository;
import com.eventociclismo.repositories.TeamRepository;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Mono;

import java.util.Objects;
import java.util.function.Function;

@Service
@Validated
public class DeleteTeamUseCase implements Function<String, Mono<Void>> {
    private final CyclistRepository cyclistRepository;
    private final TeamRepository teamRepository;
    public DeleteTeamUseCase(CyclistRepository cyclistRepository, TeamRepository teamRepository, GetTeamUseCase getTeamUseCase) {
        this.cyclistRepository = cyclistRepository;
        this.teamRepository = teamRepository;
    }
    @Override
    public Mono<Void> apply(String id) {
        Objects.requireNonNull(id, "Id is required");
        return teamRepository.deleteById(id)
                .switchIfEmpty(Mono.defer(() -> cyclistRepository.deleteByTeamId(id)));
    }
}
