package com.eventociclismo.UseCase;

import com.eventociclismo.dto.CyclistDto;
import com.eventociclismo.dto.TeamDto;
import com.eventociclismo.repositories.CyclistRepository;
import com.eventociclismo.utils.MapperUtils;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@Service
@Validated
public class AddCyclistToTeamUseCase implements Function<CyclistDto, Mono<TeamDto>> {
    private final CyclistRepository cyclistRepository;
    private final MapperUtils mapperUtils;
    private final GetTeamUseCase getTeamUseCase;

    public AddCyclistToTeamUseCase(CyclistRepository cyclistRepository, MapperUtils mapperUtils, GetTeamUseCase getTeamUseCase) {
        this.cyclistRepository = cyclistRepository;
        this.mapperUtils = mapperUtils;
        this.getTeamUseCase = getTeamUseCase;
    }

    @Override
    public Mono<TeamDto> apply(CyclistDto cyclistDto) {
        return getTeamUseCase.apply(cyclistDto.getTeamId())
                .flatMap(foundTeam ->
                    cyclistRepository.save(mapperUtils.fromDtoToCyclistEntity().apply(cyclistDto))
                            .map(savedCyclist -> {
                                foundTeam.getCyclists().add(mapperUtils.fromCyclistEntityToDto().apply(savedCyclist));
                                return foundTeam;
                            })
                );

    };
}
