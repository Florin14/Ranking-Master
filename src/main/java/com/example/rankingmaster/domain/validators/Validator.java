package com.example.rankingmaster.domain.validators;

public interface Validator<T> {
    void validate(T entity) throws ValidationException;
}