package com.eventociclismo.utils;

import com.eventociclismo.collections.Cyclist;
import com.eventociclismo.collections.Team;
import com.eventociclismo.dto.CyclistDto;
import com.eventociclismo.dto.TeamDto;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class MapperUtils {

    public Function<Cyclist, CyclistDto> fromCyclistEntityToDto() {
        return cyclist -> {
            var cyclistDto = new CyclistDto();
            cyclistDto.setId(cyclist.getId());
            cyclistDto.setTeamId(cyclist.getTeamId());
            cyclistDto.setName(cyclist.getName());
            cyclistDto.setNacionality(cyclist.getNacionality());
            cyclistDto.setCompetitionNumber(cyclist.getCompetitionNumber());
            return cyclistDto;
        };
    }

    public Function<CyclistDto, Cyclist> fromDtoToCyclistEntity() {
        return cyclistDto -> {
            var cyclist = new Cyclist();
            cyclist.setId(cyclistDto.getId());
            cyclist.setTeamId(cyclistDto.getTeamId());
            cyclist.setName(cyclistDto.getName());
            cyclist.setNacionality(cyclistDto.getNacionality());
            cyclist.setCompetitionNumber(cyclistDto.getCompetitionNumber());
            return cyclist;
        };
    }
    public Function<Team, TeamDto> fromTeamEntityToDto() {
        return team -> {
            var teamDto = new TeamDto();
            teamDto.setId(team.getId());
            teamDto.setName(team.getName());
            return teamDto;
        };
    }

    public Function<TeamDto, Team> fromDtoToTeamEntity(String id) {
        return teamDto -> {
            var team = new Team();
            team.setId(id);
            team.setName(teamDto.getName());
            return team;
        };
    }


}
