package com.eventociclismo.UseCase;

import com.eventociclismo.collections.Team;
import com.eventociclismo.dto.CyclistDto;
import com.eventociclismo.dto.TeamDto;
import com.eventociclismo.utils.MapperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Mono;

import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import java.util.function.Function;

@Service
@Validated
public class AddCyclistToTeamUseCase implements Function<CyclistDto, Mono<TeamDto>> {

    @Autowired
    ReactiveMongoTemplate mongoTemplate;
    private final MapperUtils mapperUtils;
    private final GetTeamUseCase getTeamUseCase;

    public AddCyclistToTeamUseCase(MapperUtils mapperUtils, GetTeamUseCase getTeamUseCase) {
        this.mapperUtils = mapperUtils;
        this.getTeamUseCase = getTeamUseCase;
    }

    @Override
    public Mono<TeamDto> apply(CyclistDto cyclistDto) {
        return getTeamUseCase.apply(cyclistDto.getTeamName())
                .flatMap(foundTeam -> {
                    Query query = new Query().addCriteria(Criteria.where("teamName").is(cyclistDto.getTeamName()));
                    Update update = new Update().addToSet("cyclistList", cyclistDto);
                    return mongoTemplate.findAndModify(query, update, Team.class)
                            .map(mapperUtils.fromTeamEntityToDto());
                });
    }
}
