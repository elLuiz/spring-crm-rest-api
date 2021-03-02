package com.crm.rest.service;

import com.crm.rest.dao.HibernateRepository;
import com.crm.rest.entity.Customer;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@org.springframework.stereotype.Service
public class CustomerService implements Service<Customer> {
    @Autowired
    private HibernateRepository hibernateRepository;

    @Transactional
    @Override
    public List<Customer> findAll() {
        return hibernateRepository.findAll();
    }

    @Transactional
    @Override
    public Optional<Customer> findById(int id) {
        return hibernateRepository.findById(id);
    }

    @Transactional
    @Override
    public void save(Customer object) {
        hibernateRepository.save(object);
    }

    @Transactional
    @Override
    public void deleteById(int id) {
        hibernateRepository.deleteById(id);
    }
}
