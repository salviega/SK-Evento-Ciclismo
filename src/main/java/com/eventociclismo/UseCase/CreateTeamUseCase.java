package com.eventociclismo.UseCase;

import com.eventociclismo.collections.Team;
import com.eventociclismo.dto.TeamDto;
import com.eventociclismo.repositories.TeamRepository;
import com.eventociclismo.utils.MapperUtils;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Mono;

@Service
@Validated
public class CreateTeamUseCase implements SaveTeam {

    private final TeamRepository teamRepository;
    private final MapperUtils mapperUtils;

    public  CreateTeamUseCase(TeamRepository teamRepository, MapperUtils mapperUtils) {
        this.teamRepository = teamRepository;
        this.mapperUtils = mapperUtils;
    }

    @Override
    public Mono<TeamDto> apply(TeamDto newTeamDto) {
        return teamRepository
                .save(mapperUtils.fromDtoToTeamEntity().apply(newTeamDto))
                .map(mapperUtils.fromTeamEntityToDto());
    }
}
