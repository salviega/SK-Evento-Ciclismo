package com.eventociclismo.UseCase;

import com.eventociclismo.dto.CyclistDto;
import com.eventociclismo.dto.TeamDto;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@FunctionalInterface
public interface SaveCyclist {
    Mono<TeamDto> apply(@Valid CyclistDto cyclistDto);
}
