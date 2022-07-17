package com.eventociclismo.routers;

import com.eventociclismo.UseCase.*;
import com.eventociclismo.dto.CyclistDto;
import com.eventociclismo.dto.TeamDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class QueryRouter {

    Logger log = LoggerFactory.getLogger("QueryRouter");

    public RouterFunction<ServerResponse> getAllCyclists(GetAllCyclistUseCase getAllCyclistUseCase) {
        return route(GET("/cyclists").and(accept(MediaType.APPLICATION_JSON)),
                request -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromPublisher(getAllCyclistUseCase.get(), CyclistDto.class))

        );
    }
    public RouterFunction<ServerResponse> getAllTeams(GetAllTeamsUseCase getAllTeamsUseCase) {
        return route(GET("/teams").and(accept(MediaType.APPLICATION_JSON)),
                request -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromPublisher(getAllTeamsUseCase.get(), TeamDto.class))

        );
    }
    public RouterFunction<ServerResponse> getCyclist(GetCyclistUseCase getCyclistUseCase) {
        return route(GET("/cyclists/{id}").and(accept(MediaType.APPLICATION_JSON)),
                request -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromPublisher(getCyclistUseCase.apply(request.pathVariable("id")),
                                CyclistDto.class))
        );
    }
    public RouterFunction<ServerResponse> getTeam(GetTeamUseCase getTeamUseCase) {
        return route(GET("/teams/{_id}").and(accept(MediaType.APPLICATION_JSON)),
                request -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromPublisher(getTeamUseCase.apply(request.pathVariable("_id")),
                                TeamDto.class))
        );
    }
    public RouterFunction<ServerResponse> AddCyclistToTeam(AddCyclistToTeamUseCase addCyclistToTeamUseCase) {
        return route(POST("/cyclist").and(accept(MediaType.APPLICATION_JSON)),
                request -> request.bodyToMono(CyclistDto.class)
                        .flatMap(createCyclistDto -> addCyclistToTeamUseCase.apply(createCyclistDto)
                                .flatMap(result -> ServerResponse.ok()
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .bodyValue(result)))
        );
    }
    public RouterFunction<ServerResponse> createTeam(CreateTeamUseCase createTeamUseCase) {
        return route(POST("/teams").and(accept(MediaType.APPLICATION_JSON)),
                request -> request.bodyToMono(TeamDto.class)
                        .flatMap(createTeamDto -> createTeamUseCase.apply(createTeamDto)
                                .flatMap(result -> ServerResponse.ok()
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .bodyValue(result)))
        );
    }
    public RouterFunction<ServerResponse> updateCyclist(UpdateCyclistUseCase updateCyclistUseCase) {
        return route(PUT("/cyclists/{id}").and(accept(MediaType.APPLICATION_JSON)),
                request -> request.bodyToMono(CyclistDto.class)
                        .flatMap(updateCyclistDto -> updateCyclistUseCase.apply(request.pathVariable("id"), updateCyclistDto)
                                .flatMap(result -> ServerResponse.ok()
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .bodyValue(result)))
        );
    }
    public RouterFunction<ServerResponse> updateTeam(UpdateTeamUseCase updateTeamUseCase) {
        return route(PUT("/teams/{id}").and(accept(MediaType.APPLICATION_JSON)),
                request -> request.bodyToMono(TeamDto.class)
                        .flatMap(updateTeamDto -> updateTeamUseCase.apply(request.pathVariable("id"), updateTeamDto)
                                .flatMap(result -> ServerResponse.ok()
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .bodyValue(result)))
        );
    }
    public RouterFunction<ServerResponse> deleteCyclist(DeleteCyclistUseCase deleteCyclistUseCase) {
        return route(DELETE("/cyclists/{id}").and(accept(MediaType.APPLICATION_JSON)),
                request -> ServerResponse.accepted()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromPublisher(deleteCyclistUseCase.apply(request.pathVariable("id")), Void.class))
        );
    }
    public RouterFunction<ServerResponse> deleteTeam(DeleteTeamUseCase deleteTeamUseCase) {
        return route(DELETE("/teams/{id}").and(accept(MediaType.APPLICATION_JSON)),
                request -> ServerResponse.accepted()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromPublisher(deleteTeamUseCase.apply(request.pathVariable("id")), Void.class))
        );
    }
}
