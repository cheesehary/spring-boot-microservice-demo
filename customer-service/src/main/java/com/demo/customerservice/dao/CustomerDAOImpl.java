package com.demo.customerservice.dao;

import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.demo.customerservice.entity.Customer;

@Repository
public class CustomerDAOImpl implements CustomerDAO {
	@Autowired
	private EntityManager entityManager;
	
	@Override
	public List<Customer> findAll(int page, int size) {
		Session session = entityManager.unwrap(Session.class);
		Query<Customer> query = session.createQuery("from Customer", Customer.class);
		query.setFirstResult(page * size);
		query.setMaxResults(size);
		List<Customer> customers = query.getResultList();
		return customers;
	}

	@Override
	public Customer findById(int id) {
		Session session = entityManager.unwrap(Session.class);
		Customer customer = session.find(Customer.class, id);
		return customer;
	}

	@Override
	public Customer save(Customer customer) {
		Session session = entityManager.unwrap(Session.class);
		session.saveOrUpdate(customer);
		return customer;
	}

	@Override
	public void deleteById(int id) {
		Session session = entityManager.unwrap(Session.class);
		Customer customer = session.find(Customer.class, id);
		session.delete(customer);
	}

}
