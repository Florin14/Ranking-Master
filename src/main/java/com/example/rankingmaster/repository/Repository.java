package com.example.rankingmaster.repository;

import com.example.rankingmaster.domain.Entity;

public interface Repository<Long, E extends Entity<Long>> {

    E findOne(Long id);

    Iterable<E> findAll();

    E save(E entity);

    E delete(Long id);

    void update(E entity);
}
