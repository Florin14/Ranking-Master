package com.example.rankingmaster.domain;

import java.time.LocalDate;

public class ResultDto {
    private String stage;
    private String homeTeam;
    private String awayTeam;
    private Integer resultHomeTeam;
    private Integer resultAwayTeam;

    public ResultDto(String stage, String homeTeam, String awayTeam, Integer resultHomeTeam, Integer resultAwayTeam) {
        this.stage = stage;
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.resultHomeTeam = resultHomeTeam;
        this.resultAwayTeam = resultAwayTeam;
    }

    public String getStage() {
        return stage;
    }

    public void setStage(String stage) {
        this.stage = stage;
    }

    public String getHomeTeam() {
        return homeTeam;
    }

    public void setHomeTeam(String homeTeam) {
        this.homeTeam = homeTeam;
    }

    public String getAwayTeam() {
        return awayTeam;
    }

    public void setAwayTeam(String awayTeam) {
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
}
