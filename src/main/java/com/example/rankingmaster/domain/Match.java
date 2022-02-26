package com.example.rankingmaster.domain;

import java.time.LocalDate;

public class Match extends Entity<Long> {
    private Long tournament;
    private Long homeTeam;
    private Long awayTeam;
    private Integer stage;
    private LocalDate matchDate;

    public Match(Long tournament, Long homeTeam, Long awayTeam, Integer stage, LocalDate matchDate) {
        this.tournament = tournament;
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.stage = stage;
        this.matchDate = matchDate;
    }

    public Long getTournament() {
        return tournament;
    }

    public void setTournament(Long tournament) {
        this.tournament = tournament;
    }

    public Long getHomeTeam() {
        return homeTeam;
    }

    public void setHomeTeam(Long homeTeam) {
        this.homeTeam = homeTeam;
    }

    public Long getAwayTeam() {
        return awayTeam;
    }

    public void setAwayTeam(Long awayTeam) {
        this.awayTeam = awayTeam;
    }

    public Integer getStage() {
        return stage;
    }

    public void setStage(Integer stage) {
        this.stage = stage;
    }

    public LocalDate getMatchDate() {
        return matchDate;
    }

    public void setMatchDate(LocalDate matchDate) {
        this.matchDate = matchDate;
    }
}
