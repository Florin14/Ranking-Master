package com.example.rankingmaster.service;

import com.example.rankingmaster.domain.Tournament;
import com.example.rankingmaster.domain.validators.ValidationException;
import com.example.rankingmaster.repository.Repository;

public class TournamentService {
    private final Repository<Long, Tournament> tournamentRepository;

    public TournamentService(Repository<Long, Tournament> tournamentRepository) {
        this.tournamentRepository = tournamentRepository;
    }

    public void saveTournament(Tournament tournament) {
        try {
            this.tournamentRepository.save(tournament);
        } catch (ValidationException | IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    public void deleteTournament(Long id) {
        try {
            this.tournamentRepository.delete(id);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    public Tournament findOne(Long x) {
        try {
            return this.tournamentRepository.findOne(x);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Iterable<Tournament> findAll() {
        try {
            return this.tournamentRepository.findAll();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        return null;
    }
}
