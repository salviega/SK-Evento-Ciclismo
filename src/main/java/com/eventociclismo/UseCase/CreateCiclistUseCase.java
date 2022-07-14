package com.eventociclismo.UseCase;

import com.eventociclismo.dto.CyclistDto;
import com.eventociclismo.dto.TeamDto;
import com.eventociclismo.repositories.CyclistRepository;
import com.eventociclismo.utils.MapperUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Service
@Validated
public class CreateCiclistUseCase implements SaveCyclist {

    private final CyclistRepository cyclistRepository;
    private final MapperUtils mapperUtils;
    private final GetAllCyclistFromTeamUseCase getAllCyclistFromTeamUseCase;

    Logger log = LoggerFactory.getLogger("UseCase");


    public CreateCiclistUseCase(CyclistRepository cyclistRepository,MapperUtils mapperUtils, GetAllCyclistFromTeamUseCase getAllCyclistFromTeamUseCase) {
        this.cyclistRepository = cyclistRepository;
        this.mapperUtils = mapperUtils;
        this.getAllCyclistFromTeamUseCase = getAllCyclistFromTeamUseCase;
    }

    @Override
    public Mono<TeamDto> apply(CyclistDto cyclistDto) {
        //Objects.requireNonNull(cyclistDto.getId(), "The cyclist's ID is required");
        log.info("CyclistDto" + cyclistDto);
        return getAllCyclistFromTeamUseCase.apply(cyclistDto.getTeamName()).flatMap(teamDto ->
                cyclistRepository.save(mapperUtils.fromDtoToCyclistEntity().apply(cyclistDto))
                        .map(cyclist -> {
                            teamDto.getCyclists().add(cyclist);
                            return teamDto;
                        }));
    }
}
