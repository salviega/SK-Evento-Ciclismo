package com.eventociclismo.utils;

import com.eventociclismo.collections.Cyclist;
import com.eventociclismo.collections.Team;
import com.eventociclismo.dto.CyclistDto;
import com.eventociclismo.dto.TeamDto;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class MapperUtils {
    public Function<Team, TeamDto> fromTeamEntityToDto() {
        return team -> {
            var teamDto = new TeamDto();
            teamDto.setName(team.getTeamName());
            teamDto.setCyclists(team.getCyclistList());
            return teamDto;
        };
    }

    public Function<TeamDto, Team> fromDtoToTeamEntity() {
        return teamDto -> {
            var team = new Team();
            team.setTeamName(teamDto.getName());
            team.setCyclistList(teamDto.getCyclists());
            return team;
        };
    }

    public Function<Cyclist, CyclistDto> fromCyclistEntityToDto() {
        return cyclist -> {
            var cyclistDto = new CyclistDto();
            cyclistDto.setName(cyclist.getName());
            cyclistDto.setNacionality(cyclist.getNacionality());
            cyclistDto.setCompetitionNumber(cyclist.getCompetitionNumber());
            cyclistDto.setTeamName(cyclist.getTeamName());
            return cyclistDto;
        };
    }

    public Function<CyclistDto, Cyclist> fromDtoToCyclistEntity() {
        return cyclistDto -> {
            var cyclist = new Cyclist();
            cyclist.setName(cyclistDto.getName());
            cyclist.setNacionality(cyclistDto.getNacionality());
            cyclist.setCompetitionNumber(cyclistDto.getCompetitionNumber());
            cyclist.setTeamName(cyclistDto.getTeamName());
            return cyclist;
        };
    }
}
