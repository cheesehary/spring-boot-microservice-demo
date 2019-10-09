package com.demo.customerservice.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.samePropertyValuesAs;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.demo.customerservice.dao.CustomerDAO;
import com.demo.customerservice.dao.CustomerExtDAO;
import com.demo.customerservice.entity.Customer;
import com.demo.customerservice.entity.CustomerExt;
import com.demo.customerservice.model.CustomerWithDetail;

@RunWith(MockitoJUnitRunner.class)
public class CustomerServiceTest {
	@InjectMocks
	private CustomerServiceImpl customerService;

	@Mock
	private CustomerDAO customerDAOMock;

	@Mock
	private CustomerExtDAO customerExtDAOMock;

	@Mock
	private List<String> extPropsMock;

	@Test
	public void testFindAll() {
		List<Customer> customers = new ArrayList<>();
		when(customerDAOMock.findAll(3, 20)).thenReturn(customers);
		assertEquals(customers, customerService.findAll(3, 20));
	}

	@Test
	public void testFindById_success() {
		Customer customer = new Customer();
		customer.setId(1);
		customer.setFirstName("firstName");
		customer.setLastName("lastName");
		customer.setDob(new Date(2019, 10, 9));
		when(customerDAOMock.findById(1)).thenReturn(customer);
		List<CustomerExt> exts = Arrays.asList(new CustomerExt(1, CustomerExtProp.homeAddress, "someAddress"));
		when(customerExtDAOMock.findByCustomerId(1)).thenReturn(exts);
		CustomerWithDetail customerWithDetail = new CustomerWithDetail();
		customerWithDetail.setId(1);
		customerWithDetail.setFirstName("firstName");
		customerWithDetail.setLastName("lastName");
		customerWithDetail.setDob(new Date(2019, 10, 9));
		customerWithDetail.setHomeAddress("someAddress");
		assertThat(customerService.findById(1), samePropertyValuesAs(customerWithDetail));
	}

	@Test(expected=CustomerNotFoundException.class)
	public void testFindById_failure() {
		when(customerDAOMock.findById(1)).thenReturn(null);
		customerService.findById(1);
		verify(customerDAOMock).findById(1);
	}

	@Test
	public void testSave() {
		when(extPropsMock.stream()).thenReturn(Arrays.asList(CustomerExtProp.homeAddress).stream());
		CustomerWithDetail customerWithDetail = new CustomerWithDetail();
		customerWithDetail.setFirstName("firstName");
		customerWithDetail.setLastName("lastName");
		customerWithDetail.setDob(new Date(2019, 10, 9));
		customerWithDetail.setHomeAddress("someAddress");
		CustomerWithDetail savedCustomerWithDetail = new CustomerWithDetail();
		savedCustomerWithDetail.setId(0);
		savedCustomerWithDetail.setFirstName("firstName");
		savedCustomerWithDetail.setLastName("lastName");
		savedCustomerWithDetail.setDob(new Date(2019, 10, 9));
		savedCustomerWithDetail.setHomeAddress("someAddress");
		assertThat(customerService.save(customerWithDetail), samePropertyValuesAs(savedCustomerWithDetail));
		verify(customerDAOMock).save(any(Customer.class));
		verify(customerExtDAOMock).saveMany(anyList());
	}

	@Test
	public void testUpdate() {
		when(extPropsMock.stream()).thenReturn(Arrays.asList(CustomerExtProp.homeAddress).stream());
		CustomerWithDetail customerWithDetail = new CustomerWithDetail();
		customerWithDetail.setId(1);
		customerWithDetail.setFirstName("firstName");
		customerWithDetail.setLastName("lastName");
		customerWithDetail.setDob(new Date(2019, 10, 9));
		customerWithDetail.setHomeAddress("someAddress");
		Customer customer = new Customer();
		customer.setId(1);
		customer.setFirstName("firstName");
		customer.setLastName("lastName");
		customer.setDob(new Date(2019, 10, 9));
		when(customerDAOMock.findById(1)).thenReturn(customer);
		CustomerWithDetail updatedCustomerWithDetail = new CustomerWithDetail();
		updatedCustomerWithDetail.setId(1);
		updatedCustomerWithDetail.setFirstName("firstName");
		updatedCustomerWithDetail.setLastName("lastName");
		updatedCustomerWithDetail.setDob(new Date(2019, 10, 9));
		updatedCustomerWithDetail.setHomeAddress("someAddress");
		assertThat(customerService.update(customerWithDetail), samePropertyValuesAs(updatedCustomerWithDetail));
		verify(customerDAOMock).save(any(Customer.class));
		verify(customerExtDAOMock).saveMany(anyList());
	}

	@Test
	public void testDeleteById() {
		Customer customer = new Customer();
		when(customerDAOMock.findById(1)).thenReturn(customer);
		customerService.deleteById(1);
		verify(customerDAOMock).deleteById(1);
		verify(customerExtDAOMock).deleteByCustomerId(1);
	}

}
