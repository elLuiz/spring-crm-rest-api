package com.crm.rest.dao;

import java.util.List;
import java.util.Optional;

public interface HibernateRepository<R> {
    List<R> findAll();
    Optional<R> findById(int id);
    void save(R object);
    void deleteById(int id);
}
