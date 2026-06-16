package com.bk.ipldashboard.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDate;

@Getter
@Setter
@Entity(name = "match_output")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MatchOutput {
    @Id
    @Column(name="id")
    private long id;

    @Column(name="city")
    private String city;

    @Column(name="date")
    private LocalDate date;

    @Column(name="player_of_match")
    private String playerOfMatch;

    @Column(name="venue")
    private String venue;

    @Column(name="team1")
    private String team1;

    @Column(name="team2")
    private String team2;

    @Column(name="toss_winner")
    private String tossWinner;

    @Column(name="toss_decision")
    private String tossDecision;

    @Column(name="match_winner")
    private String matchWinner;

    @Column(name="result")
    private String result;

    @Column(name="result_margin")
    private String resultMargin;

    @Column(name="umpire1")
    private String umpire1;

    @Column(name="umpire2")
    private String umpire2;

}

