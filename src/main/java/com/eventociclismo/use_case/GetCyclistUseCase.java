package com.eventociclismo.use_case;

import com.eventociclismo.dto.CyclistDto;
import com.eventociclismo.repositories.CyclistRepository;
import com.eventociclismo.utils.MapperUtils;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Mono;

import java.util.Objects;
import java.util.function.Function;

@Service
@Validated
public class GetCyclistUseCase implements Function<String, Mono<CyclistDto>> {
    private final CyclistRepository cyclistRepository;
    private final MapperUtils mapperUtils;

    public GetCyclistUseCase(CyclistRepository cyclistRepository, MapperUtils mapperUtils) {
        this.cyclistRepository = cyclistRepository;
        this.mapperUtils = mapperUtils;
    }
    @Override
    public Mono<CyclistDto> apply(String id) {
        Objects.requireNonNull(id, "The Team id is required");
        return cyclistRepository.findById(id)
                .map(mapperUtils.fromCyclistEntityToDto());
    }
}
