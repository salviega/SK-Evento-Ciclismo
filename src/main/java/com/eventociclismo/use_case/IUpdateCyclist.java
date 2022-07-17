package com.eventociclismo.use_case;

import com.eventociclismo.dto.CyclistDto;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
@FunctionalInterface
public interface IUpdateCyclist {
    public Mono<CyclistDto> apply(@Valid String id, CyclistDto cyclistDto);
}
