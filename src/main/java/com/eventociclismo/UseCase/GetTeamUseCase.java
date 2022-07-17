package com.eventociclismo.UseCase;

import com.eventociclismo.dto.TeamDto;
import com.eventociclismo.repositories.CyclistRepository;
import com.eventociclismo.repositories.TeamRepository;
import com.eventociclismo.utils.MapperUtils;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Mono;

import java.util.Objects;
import java.util.function.Function;

@Service
@Validated
public class GetTeamUseCase implements Function<String, Mono<TeamDto>> {
    private final CyclistRepository cyclistRepository;
    private final TeamRepository teamRepository;
    private final MapperUtils mapperUtils;

    public GetTeamUseCase(CyclistRepository cyclistRepository, TeamRepository teamRepository, MapperUtils mapperUtils) {
        this.cyclistRepository = cyclistRepository;
        this.teamRepository = teamRepository;
        this.mapperUtils = mapperUtils;
    }
    @Override
    public Mono<TeamDto> apply(String id) {
        Objects.requireNonNull(id, "The Team id is required");
        return teamRepository.findById(id)
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
