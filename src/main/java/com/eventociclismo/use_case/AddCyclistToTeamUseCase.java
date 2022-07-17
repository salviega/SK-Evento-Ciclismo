package com.eventociclismo.use_case;

import com.eventociclismo.dto.CyclistDto;
import com.eventociclismo.dto.TeamDto;
import com.eventociclismo.repositories.CyclistRepository;
import com.eventociclismo.utils.MapperUtils;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@Service
@Validated
public class AddCyclistToTeamUseCase implements Function<CyclistDto, Mono<String>> {
    private final CyclistRepository cyclistRepository;
    private final MapperUtils mapperUtils;
    private final GetTeamUseCase getTeamUseCase;

    public AddCyclistToTeamUseCase(CyclistRepository cyclistRepository, MapperUtils mapperUtils, GetTeamUseCase getTeamUseCase) {
        this.cyclistRepository = cyclistRepository;
        this.mapperUtils = mapperUtils;
        this.getTeamUseCase = getTeamUseCase;
    }
    @Override
    public Mono<String> apply(CyclistDto cyclistDto) {
        return  cyclistRepository.findAllByTeamId(cyclistDto.getTeamId())
                        .count()
                        .flatMap(cyclistsNumber -> {
                            if(cyclistsNumber > 7) {
                                return Mono.just("Cannot add cyclist to this team because team is completed");
                            } else {
                                return cyclistRepository.save(mapperUtils.fromDtoToCyclistEntity().apply(cyclistDto))
                                    .map(mapperUtils.fromCyclistEntityToDto())
                                    .map(cyclistDto1 -> cyclistDto1.getId());
                            }
                        });
    }
}
