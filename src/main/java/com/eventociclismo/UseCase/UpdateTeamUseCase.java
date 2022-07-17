package com.eventociclismo.UseCase;

import com.eventociclismo.dto.TeamDto;
import com.eventociclismo.repositories.TeamRepository;
import com.eventociclismo.utils.MapperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Service
@Validated
public class UpdateTeamUseCase implements SaveTeam {
    @Autowired
    ReactiveMongoTemplate mongoTemplate;
    private final TeamRepository teamRepository;
    private final MapperUtils mapperUtils;

    public UpdateTeamUseCase(TeamRepository teamRepository, MapperUtils mapperUtils) {
        this.teamRepository = teamRepository;
        this.mapperUtils = mapperUtils;
    }

    @Override
    public Mono<TeamDto> apply(TeamDto teamDto) {
        Objects.requireNonNull(teamDto.getId(), "Id of the Team is required");
        return teamRepository
                .save(mapperUtils.fromDtoToTeamEntity(teamDto.getId()).apply(teamDto))
                .map(mapperUtils.fromTeamEntityToDto());
    }
}