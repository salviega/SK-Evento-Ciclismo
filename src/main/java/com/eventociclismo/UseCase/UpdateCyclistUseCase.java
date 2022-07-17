package com.eventociclismo.UseCase;

import com.eventociclismo.collections.Cyclist;
import com.eventociclismo.dto.CyclistDto;
import com.eventociclismo.utils.MapperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Mono;

import java.util.Objects;
import java.util.function.Function;

@Service
@Validated
public class UpdateCyclistUseCase implements Function<CyclistDto, Mono<CyclistDto>> {
    @Autowired
    ReactiveMongoTemplate mongoTemplate;
    private final MapperUtils mapperUtils;

    public UpdateCyclistUseCase(MapperUtils mapperUtils) {
        this.mapperUtils = mapperUtils;
    }

    @Override
    public Mono<CyclistDto> apply(CyclistDto cyclistDto) {
        Objects.requireNonNull(cyclistDto.getId(), "Id is required");
        Query query = new Query().addCriteria(Criteria.where("_id").is(cyclistDto.getId()));
        Update update = new Update()
                .set("teamId", cyclistDto.getTeamId())
                .set("name", cyclistDto.getName())
                .set("nacionality", cyclistDto.getNacionality())
                .set("competitionName", cyclistDto.getCompetitionNumber());
        return mongoTemplate.findAndModify(query, update, Cyclist.class)
                .map(mapperUtils.fromCyclistEntityToDto());
    }
}
