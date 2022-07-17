package com.eventociclismo.use_case;

import com.eventociclismo.dto.TeamDto;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
@FunctionalInterface
public interface ISaveTeam {
    Mono<TeamDto> apply(@Valid TeamDto teamDto);

}
