package com.eventociclismo.routers;

import com.eventociclismo.UseCase.AddCyclistToTeamUseCase;
import com.eventociclismo.UseCase.CreateTeamUseCase;
import com.eventociclismo.dto.CyclistDto;
import com.eventociclismo.dto.TeamDto;
import com.eventociclismo.utils.swagger_body.CyclistBody;
import com.eventociclismo.utils.swagger_body.TeamBody;
import io.swagger.v3.oas.annotations.Operation;
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
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class QueryRouter {

    Logger log = LoggerFactory.getLogger("QueryRouter");

    @Bean
    @RouterOperation(operation = @Operation(operationId = "addCyclistToTeam", summary = "Add cyclist to Team", tags = "Cyclist",
            requestBody = @RequestBody(required = true, description = "Add a Cyclist",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = CyclistBody.class))
                    }),
            responses = {@ApiResponse(responseCode = "201", description = "Inserted", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = CyclistBody.class))
            })}
    ))
    public RouterFunction<ServerResponse> AddCyclistToTeam(AddCyclistToTeamUseCase addCyclistToTeamUseCase) {
        return route(PUT("/AddCyclist").and(accept(MediaType.APPLICATION_JSON)),
                request -> request.bodyToMono(CyclistDto.class)
                        .flatMap(createCyclistDto -> addCyclistToTeamUseCase.apply(createCyclistDto)
                                .flatMap(result -> ServerResponse.ok()
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .bodyValue(result)))
                );
    }

    @Bean
    @RouterOperation(operation = @Operation(operationId = "createTeam", summary = "Create a team", tags = "Team",
            requestBody = @RequestBody(required = true, description = "Create Team",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = TeamBody.class))
                    }),
            responses = {@ApiResponse(responseCode = "200", description = "Created", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = TeamBody.class))
            })}
    ))
    public RouterFunction<ServerResponse> createTeam(CreateTeamUseCase createTeamUseCase) {
        return route(POST("/createTeam").and(accept(MediaType.APPLICATION_JSON)),
                request -> request.bodyToMono(TeamDto.class)
                        .flatMap(creteTeamDto -> createTeamUseCase.apply(creteTeamDto)
                                .flatMap(result -> ServerResponse.ok()
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .bodyValue(result)))
        );
    }
}
