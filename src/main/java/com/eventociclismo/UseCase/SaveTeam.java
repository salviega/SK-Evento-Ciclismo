package com.eventociclismo.UseCase;

import com.eventociclismo.dto.TeamDto;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@FunctionalInterface
public interface SaveTeam {
    Mono<TeamDto> apply(@Valid TeamDto teamDto);

}
