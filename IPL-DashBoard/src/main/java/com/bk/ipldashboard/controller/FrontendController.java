package com.bk.ipldashboard.controller;

import com.bk.ipldashboard.model.Team;
import com.bk.ipldashboard.repository.MatchRepository;
import com.bk.ipldashboard.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class FrontendController {

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private MatchRepository matchRepository;

    @GetMapping("/")
    public String getHomePage(Model model) {
        Iterable<Team> teams = teamRepository.findAll();
        model.addAttribute("teams", teams);
        return "index";
    }

    @GetMapping("/dashboard/{teamName}")
    public String getTeamDashboard(@PathVariable String teamName, Model model) {
        Team team = teamRepository.findByTeamName(teamName);
        if (team != null) {
            team.setMatches(matchRepository.findLatestMatchesByTeamName(team.getTeamName(), 4));
        }
        model.addAttribute("team", team);
        return "team-dashboard";
    }
}
