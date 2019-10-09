package com.demo.customerservice.dao;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.samePropertyValuesAs;
import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.demo.customerservice.entity.CustomerExt;
import com.demo.customerservice.service.CustomerExtProp;

@RunWith(SpringRunner.class)
@Transactional
@SpringBootTest
public class CustomerExtDAOTest {
	@Autowired
	private CustomerExtDAO customerExtDAO;

	@Test
	public void testFindByCustomerId() {
		List<CustomerExt> customerExts = customerExtDAO.findByCustomerId(1);
		assertEquals(1, customerExts.size());
		assertEquals(1, customerExts.get(0).getCustomerId());
	}

	@Test
	public void testSave() {
		CustomerExt customerExt2 = new CustomerExt(2, CustomerExtProp.homeAddress, "brown street");
		assertThat(customerExtDAO.save(customerExt2), samePropertyValuesAs(customerExt2));
	}

	@Test
	public void testSaveMany() {
		List<CustomerExt> exts = Arrays.asList(new CustomerExt(2, CustomerExtProp.homeAddress, "brown street"),
				new CustomerExt(3, CustomerExtProp.homeAddress, "network street"));
		List<CustomerExt> savedExts = customerExtDAO.saveMany(exts);
		assertEquals(2, savedExts.size());
		assertEquals(1, customerExtDAO.findByCustomerId(2).size());
		assertEquals(1, customerExtDAO.findByCustomerId(3).size());
	}

	@Test
	public void testDeleteByCustomerId() {
		customerExtDAO.deleteByCustomerId(1);
		assertThat(customerExtDAO.findByCustomerId(1), empty());
	}
}
