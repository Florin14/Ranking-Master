package com.example.rankingmaster.service;

import com.example.rankingmaster.domain.Match;
import com.example.rankingmaster.domain.validators.ValidationException;
import com.example.rankingmaster.repository.Repository;

public class MatchService {
    private final Repository<Long, Match> matchRepository;

    public MatchService(Repository<Long, Match> matchRepository) {
        this.matchRepository = matchRepository;
    }

    public void saveMatch(Match match) {
        try {
            this.matchRepository.save(match);
        } catch (ValidationException | IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    public void deleteMatch(Long id) {
        try {
            this.matchRepository.delete(id);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    public Match findOne(Long x) {
        try {
            return this.matchRepository.findOne(x);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Iterable<Match> findAll() {
        try {
            return this.matchRepository.findAll();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        return null;
    }
}
