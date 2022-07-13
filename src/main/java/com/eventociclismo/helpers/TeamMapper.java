package com.eventociclismo.helpers;

import com.eventociclismo.collections.Cyclist;
import com.eventociclismo.collections.Team;
import com.eventociclismo.dto.CyclistDto;
import com.eventociclismo.dto.TeamDto;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class TeamMapper {
    public Function<Team, TeamDto> fromTeamEntityToDto() {
        return team -> {
            var teamDto = new TeamDto();
            teamDto.setId(team.getId());
            teamDto.setName(team.getName());
            teamDto.setCyclistList(team.getCyclistList());
            return teamDto;
        };
    }

    public Function<TeamDto, Team> fromDtoToTeamEntity() {
        return teamDto -> {
            var team = new Team();
            team.setId(teamDto.getId());
            team.setName(teamDto.getName());
            team.setCyclistList(teamDto.getCyclistList());
            return team;
        };
    }

    public Function<Cyclist, CyclistDto> fromCyclistEntityToDto() {
        return cyclist -> {
            var cyclistDto = new CyclistDto();
            cyclistDto.setId(cyclist.getId());
            cyclistDto.setName(cyclist.getName());
            cyclistDto.setNacionality(cyclist.getNacionality());
            cyclistDto.setCompetitionNumber(cyclist.getCompetitionNumber());
            cyclistDto.setTeamName(cyclistDto.getTeamName());
            return cyclistDto;
        };
    }

    public Function<CyclistDto, Cyclist> fromDtoToCyclistEntity() {
        return cyclistDto -> {
            var cyclist = new Cyclist();
            cyclist.setId(cyclistDto.getId());
            cyclist.setName(cyclistDto.getName());
            cyclist.setNacionality(cyclistDto.getNacionality());
            cyclist.setCompetitionNumber(cyclistDto.getCompetitionNumber());
            cyclist.setTeamName(cyclist.getTeamName());
            return cyclist;
        };
    }
}
