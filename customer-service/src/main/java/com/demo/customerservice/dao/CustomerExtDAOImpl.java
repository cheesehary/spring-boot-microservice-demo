package com.demo.customerservice.dao;

import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.demo.customerservice.entity.CustomerExt;

@Repository
public class CustomerExtDAOImpl implements CustomerExtDAO {
	@Autowired
	private EntityManager entityManager;

	@Override
	public List<CustomerExt> findByCustomerId(int id) {
		Session session = entityManager.unwrap(Session.class);
		Query<CustomerExt> query = session.createQuery("from CustomerExt c where c.customerId=:id", CustomerExt.class);
		query.setParameter("id", id);
		return query.getResultList();
	}

	@Override
	public CustomerExt save(CustomerExt customerExt) {
		Session session = entityManager.unwrap(Session.class);
		session.saveOrUpdate(customerExt);
		return customerExt;
	}

	@Override
	public List<CustomerExt> saveMany(List<CustomerExt> customerExts) {
		customerExts.stream().forEach(customerExt -> this.save(customerExt));
		return customerExts;
	}

	@Override
	public void deleteByCustomerId(int id) {
		Session session = entityManager.unwrap(Session.class);
		Query query = session.createQuery("delete from CustomerExt c where c.customerId=:id");
		query.setParameter("id", id);
		query.executeUpdate();
	}

}
