package com.example.rankingmaster.domain;

public class Result extends Entity<Long> {
    private Long tournament;
    private Integer stage;
    private Long homeTeam;
    private Long awayTeam;
    private Integer resultHomeTeam;
    private Integer resultAwayTeam;

    public Result(Long tournament, Integer stage, Long homeTeam, Long awayTeam, Integer resultHomeTeam, Integer resultAwayTeam) {
        this.tournament = tournament;
        this.stage = stage;
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.resultHomeTeam = resultHomeTeam;
        this.resultAwayTeam = resultAwayTeam;
    }

    public Long getTournament() {
        return tournament;
    }

    public void setTournament(Long tournament) {
        this.tournament = tournament;
    }

    public Integer getStage() {
        return stage;
    }

    public void setStage(Integer stage) {
        this.stage = stage;
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

    public Integer getResultHomeTeam() {
        return resultHomeTeam;
    }

    public void setResultHomeTeam(Integer resultHomeTeam) {
        this.resultHomeTeam = resultHomeTeam;
    }

    public Integer getResultAwayTeam() {
        return resultAwayTeam;
    }

    public void setResultAwayTeam(Integer resultAwayTeam) {
        this.resultAwayTeam = resultAwayTeam;
    }

    @Override
    public String toString() {
        return "Result{" +
                "tournament=" + tournament +
                ", stage=" + stage +
                ", homeTeam=" + homeTeam +
                ", awayTeam=" + awayTeam +
                ", resultHomeTeam=" + resultHomeTeam +
                ", resultAwayTeam=" + resultAwayTeam +
                '}';
    }
}
