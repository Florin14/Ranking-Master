package com.example.rankingmaster.domain;

public class Team extends Entity<Long> {
    private String name;
    private Long tournament;
    private Integer played;
    private Integer wins;
    private Integer draws;
    private Integer loses;
    private Integer scored;
    private Integer conceded;
    private Integer diference;
    private Integer points;

    public Team(String name, Integer gamesPlayed, Integer wins, Integer draws, Integer loses, Integer goalsScored, Integer goalsConceded, Integer points, Long tournament) {
        this.name = name;
        this.tournament = tournament;
        this.played = gamesPlayed;
        this.wins = wins;
        this.draws = draws;
        this.loses = loses;
        this.scored = goalsScored;
        this.conceded = goalsConceded;
        this.points = points;
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

    public Integer getGoalsScored() {
        return scored;
    }

    public void setGoalsScored(Integer goalsScored) {
        this.scored = goalsScored;
    }

    public Integer getGoalsConceded() {
        return conceded;
    }

    public void setGoalsConceded(Integer goalsConceded) {
        this.conceded = goalsConceded;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public Integer getDiference() {
        return getGoalsScored() - getGoalsConceded();
    }


    public Long getTournament() {
        return tournament;
    }

    public void setTournament(Long tournament) {
        this.tournament = tournament;
    }

    @Override
    public String toString() {
        return "Team{" +
                "name='" + name + '\'' +
                ", tournament=" + tournament +
                ", played=" + played +
                ", wins=" + wins +
                ", draws=" + draws +
                ", loses=" + loses +
                ", scored=" + scored +
                ", conceded=" + conceded +
                ", points=" + points +
                '}';
    }
}
