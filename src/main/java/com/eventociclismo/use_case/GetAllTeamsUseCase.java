package com.eventociclismo.use_case;

import com.eventociclismo.dto.TeamDto;
import com.eventociclismo.repositories.CyclistRepository;
import com.eventociclismo.repositories.TeamRepository;
import com.eventociclismo.utils.MapperUtils;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.function.Function;
import java.util.function.Supplier;

@Service
@Validated
public class GetAllTeamsUseCase implements Supplier<Flux<TeamDto>> {
    private final CyclistRepository cyclistRepository;
    private final TeamRepository teamRepository;
    private final MapperUtils mapperUtils;

    public GetAllTeamsUseCase(CyclistRepository cyclistRepository, TeamRepository teamRepository, MapperUtils mapperUtils) {
        this.cyclistRepository = cyclistRepository;
        this.teamRepository = teamRepository;
        this.mapperUtils = mapperUtils;
    }

    @Override
    public Flux<TeamDto> get() {
        return teamRepository.findAll()
                .map(mapperUtils.fromTeamEntityToDto())
                .flatMap(mapTeamAggregate());
    }

    private Function<TeamDto, Mono<TeamDto>> mapTeamAggregate() {
        return teamDTO ->
                Mono.just(teamDTO).zipWith(
                        cyclistRepository.findAllByTeamId(teamDTO.getId())
                                .map(mapperUtils.fromCyclistEntityToDto())
                                .collectList(),
                        (team, cyclists) -> {
                            team.setCyclists(cyclists);
                            return team;
                        }
                );
    }
}