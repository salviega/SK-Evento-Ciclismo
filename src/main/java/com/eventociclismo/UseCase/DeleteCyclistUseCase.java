package com.eventociclismo.UseCase;

import com.eventociclismo.repositories.CyclistRepository;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Mono;

import java.util.Objects;
import java.util.function.Function;

@Service
@Validated
public class DeleteCyclistUseCase implements Function<String, Mono<Void>> {
    private final CyclistRepository cyclistRepository;

    public DeleteCyclistUseCase(CyclistRepository cyclistRepository) {
        this.cyclistRepository = cyclistRepository;
    }

    @Override
    public Mono<Void> apply(String id) {
        Objects.requireNonNull(id, "Id is required");
        return cyclistRepository.deleteById(id)
                .doOnError(throwable -> Mono.error(throwable.getCause()));
    }
}
