package com.eventociclismo.use_case;

import com.eventociclismo.dto.CyclistDto;
import com.eventociclismo.repositories.CyclistRepository;
import com.eventociclismo.utils.MapperUtils;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Service
@Validated
public class UpdateCyclistUseCase implements IUpdateCyclist {
    private final CyclistRepository cyclistRepository;
    private final MapperUtils mapperUtils;
    private final GetCyclistUseCase getCyclistUseCase;

    public UpdateCyclistUseCase(CyclistRepository cyclistRepository, MapperUtils mapperUtils, GetCyclistUseCase getCyclistUseCase) {

        this.cyclistRepository = cyclistRepository;
        this.mapperUtils = mapperUtils;
        this.getCyclistUseCase = getCyclistUseCase;
    }
    @Override
    public Mono<CyclistDto> apply(String id, CyclistDto cyclistDto) {
            Objects.requireNonNull(id, "Id of the Team is required");
            return getCyclistUseCase.apply(id)
                .flatMap(foundCyclistDto -> {
                    foundCyclistDto.setTeamId(cyclistDto.getTeamId());
                    foundCyclistDto.setName(cyclistDto.getName());
                    foundCyclistDto.setNacionality(cyclistDto.getNacionality());
                    foundCyclistDto.setCompetitionNumber(cyclistDto.getCompetitionNumber());
                    return cyclistRepository
                            .save(mapperUtils.fromDtoToCyclistEntity().apply(foundCyclistDto))
                            .map(mapperUtils.fromCyclistEntityToDto());
                         });
        }
}
