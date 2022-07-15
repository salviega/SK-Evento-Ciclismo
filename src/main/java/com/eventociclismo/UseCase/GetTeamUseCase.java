package com.eventociclismo.UseCase;

import com.eventociclismo.collections.Cyclist;
import com.eventociclismo.dto.TeamDto;
import com.eventociclismo.repositories.CyclistRepository;
import com.eventociclismo.repositories.TeamRepository;
import com.eventociclismo.utils.MapperUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Mono;

import java.util.HashSet;
import java.util.Objects;
import java.util.function.Function;

@Service
@Validated
public class GetTeamUseCase implements Function<String, Mono<TeamDto>> {

    private CyclistRepository cyclistRepository;
    private TeamRepository teamRepository;
    private MapperUtils mapperUtils;

    public GetTeamUseCase(CyclistRepository cyclistRepository, TeamRepository teamRepository, MapperUtils mapperUtils) {
        this.cyclistRepository = cyclistRepository;
        this.teamRepository = teamRepository;
        this.mapperUtils = mapperUtils;
    }

    @Override
    public Mono<TeamDto> apply(String name) {
        Objects.requireNonNull(name, "The Team name is required");
        return teamRepository.findByTeamName(name)
                .map(mapperUtils.fromTeamEntityToDto());
    }
}
