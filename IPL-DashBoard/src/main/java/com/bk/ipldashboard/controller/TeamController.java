package com.bk.ipldashboard.controller;

import com.bk.ipldashboard.model.Team;
import com.bk.ipldashboard.repository.MatchRepository;
import com.bk.ipldashboard.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TeamController {

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private MatchRepository matchRepository;

    @GetMapping("/team/{teamName}")
    public Team getTeam(@PathVariable String teamName){
        Team team =  teamRepository.findByTeamName(teamName);
        team.setMatches(matchRepository.findLatestMatchesByTeamName(team.getTeamName(),4));
        return team;
    }
}
