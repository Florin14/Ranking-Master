package com.example.rankingmaster.service;

import com.example.rankingmaster.domain.Team;
import com.example.rankingmaster.domain.Tournament;
import com.example.rankingmaster.domain.validators.ValidationException;
import com.example.rankingmaster.repository.Repository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class TeamService {
    private final Repository<Long, Team> teamRepository;

    public TeamService(Repository<Long, Team> teamRepository) {
        this.teamRepository = teamRepository;
    }

    public void saveTeam(Team team) {
        try {
            this.teamRepository.save(team);
        } catch (ValidationException | IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    public void deleteTeam(Long id) {
        try {
            this.teamRepository.delete(id);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    public void updateTeam(Team team) {
        try {
            this.teamRepository.update(team);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    public Team findOne(Long x) {
        try {
            return this.teamRepository.findOne(x);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Iterable<Team> findAll() {
        try {
            return this.teamRepository.findAll();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Team> showTeams(Long id) {
        List<Team> teams = new ArrayList<>();
        for (Team team : findAll()) {
            if (id.equals(team.getTournament())) {
                teams.add(team);
            }
        }
        List<Team> sortedByGoals = teams.stream()
                .sorted(Comparator.comparing(Team::getDiference, Comparator.reverseOrder()))
                .collect(Collectors.toList());
        List<Team> sortedTeams = sortedByGoals.stream()
                .sorted(Comparator.comparing(Team::getPoints, Comparator.reverseOrder()))
                .collect(Collectors.toList());

        return sortedTeams;
    }
}
