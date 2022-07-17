package com.eventociclismo.UseCase;

import com.eventociclismo.dto.TeamDto;
import com.eventociclismo.repositories.TeamRepository;
import com.eventociclismo.utils.MapperUtils;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Service
@Validated
public class UpdateTeamUseCase implements IUpdateTeam {

    private final TeamRepository teamRepository;
    private final MapperUtils mapperUtils;
    private final GetTeamUseCase getTeamUseCase;

    public UpdateTeamUseCase(TeamRepository teamRepository, MapperUtils mapperUtils, GetTeamUseCase getTeamUseCase) {
        this.teamRepository = teamRepository;
        this.mapperUtils = mapperUtils;
        this.getTeamUseCase = getTeamUseCase;
    }
    @Override
    public Mono<TeamDto> apply(String id, TeamDto teamDto) {
        Objects.requireNonNull(id, "Id of the Team is required");
        return getTeamUseCase.apply(id)
                .flatMap(foundTeamDto -> {
                    foundTeamDto.setName(teamDto.getName());
                    return teamRepository
                            .save(mapperUtils.fromDtoToTeamEntity(foundTeamDto.getId()).apply(foundTeamDto))
                            .map(mapperUtils.fromTeamEntityToDto());
                });
    }
}
