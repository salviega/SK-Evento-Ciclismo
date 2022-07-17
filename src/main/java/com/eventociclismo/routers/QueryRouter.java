package com.eventociclismo.routers;

import com.eventociclismo.UseCase.*;
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

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class QueryRouter {

    Logger log = LoggerFactory.getLogger("QueryRouter");

    @Bean
    @RouterOperation(operation = @Operation(operationId = "getAllTeams", summary = "Get all teams", tags = "Team",
            responses = {@ApiResponse(responseCode = "200", description = "Successful", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = TeamBody.class))
            })}
    ))
    public RouterFunction<ServerResponse> getAllTeams(GetAllTeamsUseCase getAllTeamsUseCase) {
        return route(GET("/teams").and(accept(MediaType.APPLICATION_JSON)),
                request -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromPublisher(getAllTeamsUseCase.get(), TeamDto.class))

        );
    }
    @Bean
    @RouterOperation(operation = @Operation(operationId = "getAllCyclists", summary = "Get all cyclists", tags = "Cyclist",
            responses = {@ApiResponse(responseCode = "200", description = "Successful", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = TeamBody.class))
            })}
    ))
    public RouterFunction<ServerResponse> getAllCyclists(GetAllCyclistUseCase getAllCyclistUseCase) {
        return route(GET("/cyclist").and(accept(MediaType.APPLICATION_JSON)),
                request -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromPublisher(getAllCyclistUseCase.get(), CyclistDto.class))

        );
    }
    @Bean
    @RouterOperation(operation = @Operation(operationId = "getTeam", summary = "Get a team", tags = "Team",
            parameters = {
                    @Parameter(in = ParameterIn.PATH, name = "ID",
                            description = "Team ID",
                            required = true)},
            responses = {@ApiResponse(responseCode = "200", description = "Successful", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = TeamDto.class))
            })}
    ))
    public RouterFunction<ServerResponse> getTeam(GetTeamUseCase getTeamUseCase) {
        return route(GET("/teams/{_id}").and(accept(MediaType.APPLICATION_JSON)),
                request -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromPublisher(getTeamUseCase.apply(request.pathVariable("_id")),
                                TeamDto.class))
        );
    }
    @Bean
    @RouterOperation(operation = @Operation(operationId = "getCyclist", summary = "Get a cyclist", tags = "Cyclist",
            parameters = {
                    @Parameter(in = ParameterIn.PATH, name = "Cyclist ID",
                            description = "ID",
                            required = true)},
            responses = {@ApiResponse(responseCode = "200", description = "Successful", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = CyclistBody.class))
            })}
    ))
    public RouterFunction<ServerResponse> getCyclist(GetCyclistUseCase getCyclistUseCase) {
        return route(GET("/cyclists/{_id}").and(accept(MediaType.APPLICATION_JSON)),
                request -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromPublisher(getCyclistUseCase.apply(request.pathVariable("_id")),
                                CyclistDto.class))
        );
    }
    @Bean
    @RouterOperation(operation = @Operation(operationId = "createTeam", summary = "Create a team", tags = "Team",
            requestBody = @RequestBody(required = true, description = "Create team",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = TeamBody.class))
                    }),
            responses = {@ApiResponse(responseCode = "200", description = "Successful", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = TeamBody.class))
            })}
    ))
    public RouterFunction<ServerResponse> createTeam(CreateTeamUseCase createTeamUseCase) {
        return route(POST("/teams").and(accept(MediaType.APPLICATION_JSON)),
                request -> request.bodyToMono(TeamDto.class)
                        .flatMap(createTeamDto -> createTeamUseCase.apply(createTeamDto)
                                .flatMap(result -> ServerResponse.ok()
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .bodyValue(result)))
        );
    }
    @Bean
    @RouterOperation(operation = @Operation(operationId = "addCyclistToTeam", summary = "Add cyclist to team", tags = "Cyclist",
            requestBody = @RequestBody(required = true, description = "Add a cyclist",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = CyclistBody.class))
                    }),
            responses = {@ApiResponse(responseCode = "201", description = "Successful", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = CyclistBody.class))
            })}
    ))
    public RouterFunction<ServerResponse> AddCyclistToTeam(AddCyclistToTeamUseCase addCyclistToTeamUseCase) {
        return route(POST("/cyclist").and(accept(MediaType.APPLICATION_JSON)),
                request -> request.bodyToMono(CyclistDto.class)
                        .flatMap(createCyclistDto -> addCyclistToTeamUseCase.apply(createCyclistDto)
                                .flatMap(result -> ServerResponse.ok()
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .bodyValue(result)))
        );
    }

    /*@Bean
    @RouterOperation(operation = @Operation(operationId = "deleteTeam", summary = "Delete a team", tags = "Team",
            parameters = {
                    @Parameter(in = ParameterIn.PATH, name = "name",
                            description = "Team name",
                            required = true)},
            responses = {@ApiResponse(responseCode = "200", description = "Team removed", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = TeamBody.class))
            })}
    ))
    public RouterFunction<ServerResponse> deleteTeam(DeleteTeamUseCase deleteTeamUseCase) {
        return route(DELETE("/deleteTeam/{name}").and(accept(MediaType.APPLICATION_JSON)),
                request -> ServerResponse.accepted()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromPublisher(deleteTeamUseCase.apply(request.pathVariable("name")), Void.class))
        );
    }*/
}
