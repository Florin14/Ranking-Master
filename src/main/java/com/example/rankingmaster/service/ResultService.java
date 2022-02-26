package com.example.rankingmaster.service;

import com.example.rankingmaster.domain.Match;
import com.example.rankingmaster.domain.Result;
import com.example.rankingmaster.domain.validators.ValidationException;
import com.example.rankingmaster.repository.Repository;

public class ResultService {
    private final Repository<Long, Result> resultRepository;

    public ResultService(Repository<Long, Result> resultRepository) {
        this.resultRepository = resultRepository;
    }

    public void saveResult(Result result) {
        try {
            this.resultRepository.save(result);
        } catch (ValidationException | IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    public void deleteResult(Long id) {
        try {
            this.resultRepository.delete(id);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    public Result findOne(Long x) {
        try {
            return this.resultRepository.findOne(x);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Iterable<Result> findAll() {
        try {
            return this.resultRepository.findAll();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        return null;
    }
}
