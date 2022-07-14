package com.eventociclismo.routers;

import com.eventociclismo.UseCase.CreateCiclistUseCase;
import com.eventociclismo.UseCase.CreateTeamUseCase;
import com.eventociclismo.UseCase.GetAllCyclistFromTeamUseCase;
import com.eventociclismo.collections.Cyclist;
import com.eventociclismo.dto.CyclistDto;
import com.eventociclismo.dto.TeamDto;
import com.eventociclismo.utils.swagger_body.CyclistBody;
import com.eventociclismo.utils.swagger_body.TeamBody;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springdoc.core.annotations.RouterOperation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.function.Function;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class QueryRouter {

    Logger log = LoggerFactory.getLogger("QueryRouter");

    /*@Bean
    @RouterOperation(operation = @Operation(operationId = "getAllCyclistsByTeamName", summary = "All cyclists from team",
            responses = {@ApiResponse(responseCode = "200", description = "Successful", content =  {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = CyclistBody.class))
            })}
    ))
    public RouterFunction<ServerResponse> getAllCyclistByTeamName(GetAllCyclistFromTeamUseCase getAllCyclistFromTeamUseCase) {
        return route(GET("/getAllCyclist/{teamName}"),
                request -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromPublisher(
                                getAllCyclistFromTeamUseCase.apply(request.pathVariable("teamName")),
                                CyclistDto.class
                        )));
    }*/


    @Bean
    @RouterOperation(operation = @Operation(operationId = "addCyclist", summary = "Add an cyclist", tags = "Cyclist",
            requestBody = @RequestBody(required = true, description = "Insert a Cyclist",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = CyclistBody.class))
                    }),
            responses = {@ApiResponse(responseCode = "201", description = "Created", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = CyclistBody.class))
            })}
    ))
    public RouterFunction<ServerResponse> createCyclist(CreateCiclistUseCase createCiclistUseCase) {
        return route(POST("/createCyclist").and(accept(MediaType.APPLICATION_JSON)),
                request -> request.bodyToMono(CyclistDto.class)
                        .flatMap(createCyclistDto -> createCiclistUseCase.apply(createCyclistDto)
                                .flatMap(result -> ServerResponse.ok()
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .bodyValue(result)))
                );
    }

    @Bean
    @RouterOperation(operation = @Operation(operationId = "createTeam", summary = "Create a team", tags = "Team",
    responses = {@ApiResponse(responseCode = "201", description = "Created", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = TeamBody.class))
    })},
    requestBody = @RequestBody(required = true, description = "Insert a Team",
    content = {@Content(mediaType = "application/json",
    schema = @Schema(implementation = TeamBody.class))})
    ))
    public RouterFunction<ServerResponse> createTeam(CreateTeamUseCase createTeamUseCase) {
        Function<TeamDto, Mono<ServerResponse>> executor = TeamDto -> createTeamUseCase.apply(TeamDto)
                .flatMap(result -> ServerResponse.ok()
                        .contentType(MediaType.TEXT_PLAIN)
                        .bodyValue(result));
        return route(POST("/createTeam").and(accept(MediaType.APPLICATION_JSON)),
                request -> request.bodyToMono(TeamDto.class).flatMap(executor)
        );
    }
}
