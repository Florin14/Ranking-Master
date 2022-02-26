package com.example.rankingmaster.domain;

public class TeamDto {
    private Integer position;
    private String name;
    private Integer played;
    private Integer wins;
    private Integer draws;
    private Integer loses;
    private String goals;
    private Integer difference;
    private Integer points;

    public TeamDto(Integer position, String name, Integer gamesPlayed, Integer wins, Integer draws, Integer loses, String goals, Integer difference, Integer points) {
        this.position = position;
        this.name = name;
        this.played = gamesPlayed;
        this.wins = wins;
        this.draws = draws;
        this.loses = loses;
        this.goals = goals;
        this.difference = difference;
        this.points = points;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getGamesPlayed() {
        return played;
    }

    public void setGamesPlayed(Integer gamesPlayed) {
        this.played = gamesPlayed;
    }

    public Integer getWins() {
        return wins;
    }

    public void setWins(Integer wins) {
        this.wins = wins;
    }

    public Integer getDraws() {
        return draws;
    }

    public void setDraws(Integer draws) {
        this.draws = draws;
    }

    public Integer getLoses() {
        return loses;
    }

    public void setLoses(Integer loses) {
        this.loses = loses;
    }

    public String getGoals() {
        return goals;
    }

    public void setGoals(String goals) {
        this.goals = goals;
    }

    public Integer getDifference() {
        return difference;
    }

    public void setDifference(Integer difference) {
        this.difference = difference;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }
}