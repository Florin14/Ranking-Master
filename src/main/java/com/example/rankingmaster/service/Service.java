package com.example.rankingmaster.service;

import com.example.rankingmaster.domain.Match;
import com.example.rankingmaster.domain.Result;
import com.example.rankingmaster.domain.Team;
import com.example.rankingmaster.domain.Tournament;


import java.time.LocalDate;
import java.util.*;

public class Service {

    private final TournamentService tournamentService;
    private final TeamService teamService;
    private final MatchService matchService;
    private final ResultService resultService;

    private Long currentTournamentId = 0L;
    private Tournament currentTournament;

    public Service(TournamentService tournamentService, TeamService teamService, MatchService matchService, ResultService resultService) {
        this.tournamentService = tournamentService;
        this.teamService = teamService;
        this.matchService = matchService;
        this.resultService = resultService;
    }

    public void login(String password) {
        for (Tournament tournament : tournamentService.findAll()) {
            if (tournament.getPassword().equals(password)) {
                currentTournamentId = tournament.getId();
                tournament.setId(currentTournamentId);
                currentTournament = tournamentService.findOne(currentTournamentId);
            }
        }
    }

    public boolean createTournament(String name, String password, Integer teams) {
        boolean validatedSignUp = false;
        try {
            Tournament newTournament = new Tournament(name, password, teams);
            newTournament.setId(newTournament.getId());
            tournamentService.saveTournament(newTournament);
            validatedSignUp = true;
            currentTournamentId = newTournament.getId();
        } catch (NullPointerException | IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
        login(password);
        return validatedSignUp;
    }

    public void deleteTournament() {
        for (Tournament tournament : tournamentService.findAll()) {
            if (tournament.equals(tournamentService.findOne(currentTournamentId))) {
                currentTournamentId = tournament.getId();
                if (currentTournamentId == null) {
                    return;
                }

                for (Team team : showTeams()) {
                    teamService.deleteTeam(team.getId());
                }

                for (Match match : printAllMatches()) {
                    matchService.deleteMatch(match.getId());
                }

                for (Result result : printAllResults()) {
                    resultService.deleteResult(result.getId());
                }

                tournamentService.deleteTournament(currentTournamentId);
                currentTournamentId = null;
            }
        }
    }

    public void logoutTournament() {
        currentTournamentId = null;
    }

    public Long getCurrentTournamentId() {
        return currentTournamentId;
    }

    public Tournament getCurrentTournament() {
        return tournamentService.findOne(currentTournamentId);
    }

    public Tournament findOneTournament(Long x) {
        return this.tournamentService.findOne(x);
    }

    public Iterable<Tournament> getAllTournaments() {
        Iterable<Tournament> allUsers = tournamentService.findAll();
        List<Tournament> getAll = new ArrayList<>();
        for (Tournament tournament : allUsers) {
            if (!tournament.equals(currentTournament)) {
                getAll.add(tournament);
            }
        }
        return getAll;
    }

    ///CRUD TEAMS

    public void saveTeam(String name) {
        teamService.saveTeam(new Team(name, 0, 0, 0, 0, 0, 0, 0, currentTournamentId));
    }

    public Team findOneTeam(Long id) {
        return teamService.findOne(id);
    }

    public Iterable<Team> findAllTeams() {
        return teamService.findAll();
    }

    public List<Team> showTeams() {
        return teamService.showTeams(currentTournamentId);
    }

    ///CRUD MATCHES

    public void saveMatch(Long homeTeam, Long awayTeam, Integer stage, LocalDate date) {
        Match match = new Match(currentTournamentId, homeTeam, awayTeam, stage, date);
        matchService.saveMatch(match);
    }

    public void deleteMatch(Long id) {
        matchService.deleteMatch(id);
    }

    public Match getMatch(Long homeId, Long awayId) {
        for (Match match : printAllMatches()) {
            if (match.getHomeTeam().equals(homeId) && match.getAwayTeam().equals(awayId)) {
                return match;
            }
        }
        return null;
    }

    public Match findOneMatch(Long id) {
        return matchService.findOne(id);
    }

    public List<Match> printAllMatches() {
        List<Match> matches = new ArrayList<>();
        for (Match match : matchService.findAll()) {
            if (match.getTournament().equals(this.getCurrentTournamentId())) {
                matches.add(match);
            }
        }
        return matches;
    }

    ///CRUD RESULT
    public void saveResult(Long homeTeam, Integer stage, Long awayTeam, Integer homeResult, Integer awayResult) {
        Result result = new Result(currentTournamentId, stage, homeTeam, awayTeam, homeResult, awayResult);
        resultService.saveResult(result);
        editRanking(result);
    }

    public void deleteResult(Long id) {
        resultService.deleteResult(id);
    }

    public Result findOneResult(Long id) {
        return resultService.findOne(id);
    }

    public List<Result> printAllResults() {
        List<Result> results = new ArrayList<>();
        for (Result result : resultService.findAll()) {
            if (result.getTournament().equals(this.getCurrentTournamentId())) {
                results.add(result);
            }
        }
        return results;
    }

    public void editRanking(Result result) {
        Team homeTeam = findOneTeam(result.getHomeTeam());
        homeTeam.setId(result.getHomeTeam());
        Team awayTeam = findOneTeam(result.getAwayTeam());
        awayTeam.setId(result.getAwayTeam());


        if (result.getResultHomeTeam() > result.getResultAwayTeam()) {
            homeTeam.setGamesPlayed(homeTeam.getGamesPlayed() + 1);
            homeTeam.setWins(homeTeam.getWins() + 1);
            homeTeam.setGoalsScored(homeTeam.getGoalsScored() + result.getResultHomeTeam());
            homeTeam.setGoalsConceded(homeTeam.getGoalsConceded() + result.getResultAwayTeam());
            homeTeam.setPoints(homeTeam.getPoints() + 3);

            awayTeam.setGamesPlayed(awayTeam.getGamesPlayed() + 1);
            awayTeam.setLoses(awayTeam.getLoses() + 1);
            awayTeam.setGoalsScored(awayTeam.getGoalsScored() + result.getResultAwayTeam());
            awayTeam.setGoalsConceded(awayTeam.getGoalsConceded() + result.getResultHomeTeam());

            teamService.updateTeam(homeTeam);
            teamService.updateTeam(awayTeam);

        } else if (result.getResultHomeTeam() < result.getResultAwayTeam()) {
            awayTeam.setGamesPlayed(awayTeam.getGamesPlayed() + 1);
            awayTeam.setWins(awayTeam.getWins() + 1);
            awayTeam.setGoalsScored(awayTeam.getGoalsScored() + result.getResultAwayTeam());
            awayTeam.setGoalsConceded(awayTeam.getGoalsConceded() + result.getResultHomeTeam());
            awayTeam.setPoints(awayTeam.getPoints() + 3);

            homeTeam.setGamesPlayed(homeTeam.getGamesPlayed() + 1);
            homeTeam.setLoses(homeTeam.getLoses() + 1);
            homeTeam.setGoalsScored(homeTeam.getGoalsScored() + result.getResultHomeTeam());
            homeTeam.setGoalsConceded(homeTeam.getGoalsConceded() + result.getResultAwayTeam());

            teamService.updateTeam(homeTeam);
            teamService.updateTeam(awayTeam);
        } else if (result.getResultHomeTeam().equals(result.getResultAwayTeam())) {
            awayTeam.setGamesPlayed(awayTeam.getGamesPlayed() + 1);
            awayTeam.setDraws(awayTeam.getDraws() + 1);
            awayTeam.setGoalsScored(awayTeam.getGoalsScored() + result.getResultAwayTeam());
            awayTeam.setGoalsConceded(awayTeam.getGoalsConceded() + result.getResultHomeTeam());
            awayTeam.setPoints(awayTeam.getPoints() + 1);

            homeTeam.setGamesPlayed(homeTeam.getGamesPlayed() + 1);
            homeTeam.setDraws(homeTeam.getDraws() + 1);
            homeTeam.setGoalsScored(homeTeam.getGoalsScored() + result.getResultHomeTeam());
            homeTeam.setGoalsConceded(homeTeam.getGoalsConceded() + result.getResultAwayTeam());
            homeTeam.setPoints(homeTeam.getPoints() + 1);

            teamService.updateTeam(homeTeam);
            teamService.updateTeam(awayTeam);

        }
    }
}









