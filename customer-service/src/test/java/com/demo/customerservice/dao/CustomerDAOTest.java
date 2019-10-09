package com.demo.customerservice.dao;

import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.samePropertyValuesAs;
import static org.junit.Assert.assertEquals;

import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.demo.customerservice.entity.Customer;

@RunWith(SpringRunner.class)
@Transactional
@SpringBootTest
public class CustomerDAOTest {
	@Autowired
	private CustomerDAO customerDAO;

	@Test
	public void testFindAll() {
		List<Customer> customers = customerDAO.findAll();
		assertEquals(3, customers.size());
	}

	@Test
	public void testFindById() {
		Customer customer = customerDAO.findById(2);
		assertEquals(2, customer.getId());
	}

	@Test
	public void testSave() {
		Customer customer4 = new Customer();
		customer4.setId(4);
		customer4.setFirstName("first4");
		customer4.setLastName("last4");
		customer4.setDob(new Date(2019, 9, 04));
		assertThat(customerDAO.save(customer4), samePropertyValuesAs(customer4));
	}

	@Test
	public void testDeleteById() {
		customerDAO.deleteById(1);
		assertThat(customerDAO.findById(1), nullValue());
	}
}
