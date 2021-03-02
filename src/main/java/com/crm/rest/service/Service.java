package com.crm.rest.service;

import java.util.List;
import java.util.Optional;

public interface Service<R> {
    List<R> findAll();
    Optional<R> findById(int id);
    void save(R object);
    void deleteById(int id);
}
