package com.eventociclismo.use_case;

import com.eventociclismo.dto.CyclistDto;
import com.eventociclismo.repositories.CyclistRepository;
import com.eventociclismo.utils.MapperUtils;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Flux;

import java.util.function.Supplier;

@Service
@Validated
public class GetAllCyclistUseCase implements Supplier<Flux<CyclistDto>> {
    private final CyclistRepository cyclistRepository;
    private final MapperUtils mapperUtils;
    public GetAllCyclistUseCase(CyclistRepository cyclistRepository, MapperUtils mapperUtils) {
        this.cyclistRepository = cyclistRepository;
        this.mapperUtils = mapperUtils;
    }
    @Override
    public Flux<CyclistDto> get() {
        return cyclistRepository.findAll()
                .map(mapperUtils.fromCyclistEntityToDto());
    }
}
