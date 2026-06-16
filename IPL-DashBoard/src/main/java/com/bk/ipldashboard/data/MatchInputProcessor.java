package com.bk.ipldashboard.data;

import com.bk.ipldashboard.model.MatchOutput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

import java.time.LocalDate;

public class MatchInputProcessor implements ItemProcessor<MatchInput, MatchOutput> {
    private static final Logger log = LoggerFactory.getLogger(MatchInputProcessor.class);

    @Override
    public MatchOutput process(MatchInput matchInput) throws Exception {

        String firstInningTeam, secondInningTeam;

        if("bat".equals(matchInput.getToss_decision())){
            firstInningTeam = matchInput.getToss_winner();
            secondInningTeam = matchInput.getToss_winner().equals(matchInput.getTeam1())? matchInput.getTeam2(): matchInput.getTeam1();
        }
        else{
            secondInningTeam = matchInput.getToss_winner();
            firstInningTeam = matchInput.getToss_winner().equals(matchInput.getTeam1())? matchInput.getTeam2(): matchInput.getTeam1();
        }
        MatchOutput matchOutput = MatchOutput.builder().id(Long.parseLong(matchInput.getId()))
                .city(matchInput.getCity())
                .date(LocalDate.parse(matchInput.getDate()))
                .playerOfMatch(matchInput.getPlayer_of_match())
                .venue(matchInput.getVenue())
                .team1(firstInningTeam)
                .team2(secondInningTeam)
                .tossWinner(matchInput.getToss_winner())
                .matchWinner(matchInput.getWinner())
                .tossDecision(matchInput.getToss_decision())
                .result(matchInput.getResult())
                .resultMargin(matchInput.getResult_margin())
                .umpire1(matchInput.getUmpire1())
                .umpire2(matchInput.getUmpire2()).build();

        return matchOutput;
    }
}
