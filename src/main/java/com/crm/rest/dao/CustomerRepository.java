package com.crm.rest.dao;

import com.crm.rest.entity.Customer;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class CustomerRepository implements HibernateRepository<Customer> {
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public List<Customer> findAll() {
        Session session = sessionFactory.getCurrentSession();
        List<Customer> customers = session.createQuery("from Customer", Customer.class).getResultList();
        return customers;
    }

    @Override
    public Optional<Customer> findById(int id) {
        Session session = sessionFactory.getCurrentSession();
        return Optional.ofNullable(session.get(Customer.class, id));
    }

    @Override
    public void save(Customer object) {
        Session session = sessionFactory.getCurrentSession();
        session.save(object);
    }

    @Override
    public void deleteById(int id) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("DELETE from Customer WHERE id =:customer_id");
        query.setParameter("customer_id", id);
        query.executeUpdate();
    }
}
