package com.eventociclismo.UseCase;

import com.eventociclismo.collections.Cyclist;
import com.eventociclismo.collections.Team;
import com.eventociclismo.dto.CyclistDto;
import com.eventociclismo.dto.TeamDto;
import com.eventociclismo.repositories.CyclistRepository;
import com.eventociclismo.repositories.TeamRepository;
import com.eventociclismo.utils.MapperUtils;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;

@Service
@Validated
public class GetAllCyclistFromTeamUseCase implements Function<String, Mono<TeamDto>> {
    private final CyclistRepository cyclistRepository;
    private final TeamRepository teamRepository;
    private final MapperUtils mapperUtils;

    public GetAllCyclistFromTeamUseCase(MapperUtils mapperUtils, CyclistRepository cyclistRepository, TeamRepository teamRepository) {
        this.cyclistRepository = cyclistRepository;
        this.teamRepository = teamRepository;
        this.mapperUtils = mapperUtils;
    }
    @Override
    public Mono<TeamDto> apply(String teamName) {
        Objects.requireNonNull(teamName, "The team name is required");
        return teamRepository.findByTeamName(teamName)
                .map(mapperUtils.fromTeamEntityToDto())
                .flatMap(aggregateCyclistsToTeam());
    }

    private Function<TeamDto, Mono<TeamDto>> aggregateCyclistsToTeam() {
        return teamDto ->
                Mono.just(teamDto).zipWith(
                        cyclistRepository.findAllByTeamName(teamDto.getName())
                                .map(mapperUtils.fromCyclistEntityToDto())
                                .collectList(),
                        (team, cyclistsDto) -> {
                             var newSet = new HashSet<Cyclist>();
                             cyclistsDto.stream().map(
                                     mapperUtils.fromDtoToCyclistEntity())
                                     .map(cyclist -> newSet.add(cyclist));
                             team.setCyclists(newSet);
                            return team;
                        }
                );
    }
}
