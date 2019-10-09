package com.demo.customerservice.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.demo.customerservice.controller.v1.CustomerController;
import com.demo.customerservice.entity.Customer;
import com.demo.customerservice.model.CustomerList;
import com.demo.customerservice.model.CustomerWithDetail;
import com.demo.customerservice.service.CustomerNotFoundException;
import com.demo.customerservice.service.CustomerService;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@WebMvcTest(CustomerController.class)
public class CustomerControllerTest {
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private CustomerService customerServiceMock;

	@Test
	public void testFindAllCustomers() throws Exception {
		Customer customer = new Customer();
		customer.setId(1);
		customer.setFirstName("first");
		customer.setLastName("lastName");
		List<Customer> customers = Arrays.asList(customer);
		when(customerServiceMock.findAll()).thenReturn(customers);
		ObjectMapper mapper = new ObjectMapper();
		String jsonStr = mapper.writeValueAsString(new CustomerList(customers));
		RequestBuilder request = MockMvcRequestBuilders.get("/apiv1/customers").accept(MediaType.APPLICATION_JSON);
		mockMvc.perform(request).andExpect(status().isOk()).andExpect(content().json(jsonStr)).andReturn();
	}

	@Test
	public void testFindCustomer_success() throws Exception {
		CustomerWithDetail customerWithDetail = new CustomerWithDetail();
		customerWithDetail.setId(1);
		when(customerServiceMock.findById(1)).thenReturn(customerWithDetail);
		ObjectMapper mapper = new ObjectMapper();
		String jsonStr = mapper.writeValueAsString(customerWithDetail);
		RequestBuilder request = MockMvcRequestBuilders.get("/apiv1/customers/1")
				.accept(MediaType.APPLICATION_JSON);
		mockMvc.perform(request).andExpect(status().isOk()).andExpect(content().json(jsonStr)).andReturn();
	}

	@Test
	public void testFindCustomer_failure() throws Exception {
		when(customerServiceMock.findById(2)).thenThrow(new CustomerNotFoundException("customer-not-found"));
		RequestBuilder request = MockMvcRequestBuilders.get("/apiv1/customers/2")
				.accept(MediaType.APPLICATION_JSON);
		mockMvc.perform(request).andExpect(status().is4xxClientError())
				.andExpect(content().json("{code:404, message:customer-not-found}")).andReturn();
	}

	@Test
	public void testCreateCustomer_success() throws Exception {
		CustomerWithDetail customerWithDetail = new CustomerWithDetail();
		customerWithDetail.setId(1);
		customerWithDetail.setFirstName("first");
		customerWithDetail.setLastName("last");
		when(customerServiceMock.save(any(CustomerWithDetail.class))).thenReturn(customerWithDetail);
		ObjectMapper mapper = new ObjectMapper();
		String jsonStr = mapper.writeValueAsString(customerWithDetail);
		RequestBuilder request = MockMvcRequestBuilders.post("/apiv1/customers")
				.content(jsonStr).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON);
		mockMvc.perform(request).andExpect(status().isOk()).andExpect(content().json(jsonStr)).andReturn();
	}

	@Test
	public void testCreateCustomer_failure() throws Exception {
		CustomerWithDetail customerWithDetail = new CustomerWithDetail();
		customerWithDetail.setId(1);
		customerWithDetail.setFirstName("first");
		when(customerServiceMock.save(any(CustomerWithDetail.class))).thenReturn(customerWithDetail);
		ObjectMapper mapper = new ObjectMapper();
		String jsonStr = mapper.writeValueAsString(customerWithDetail);
		RequestBuilder request = MockMvcRequestBuilders.post("/apiv1/customers")
				.content(jsonStr).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON);
		mockMvc.perform(request).andExpect(status().is4xxClientError()).andReturn();
	}

	@Test
	public void testUpdateCustomer_success() throws Exception {
		CustomerWithDetail customerWithDetail = new CustomerWithDetail();
		customerWithDetail.setId(1);
		customerWithDetail.setFirstName("first");
		customerWithDetail.setLastName("last");
		when(customerServiceMock.update(any(CustomerWithDetail.class))).thenReturn(customerWithDetail);
		ObjectMapper mapper = new ObjectMapper();
		String jsonStr = mapper.writeValueAsString(customerWithDetail);
		RequestBuilder request = MockMvcRequestBuilders.put("/apiv1/customers")
				.content(jsonStr).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON);
		mockMvc.perform(request).andExpect(status().isOk()).andExpect(content().json(jsonStr)).andReturn();
	}

	@Test
	public void testUpdateCustomer_failure() throws Exception {
		CustomerWithDetail customerWithDetail = new CustomerWithDetail();
		customerWithDetail.setId(1);
		customerWithDetail.setFirstName("first");
		when(customerServiceMock.update(any(CustomerWithDetail.class))).thenReturn(customerWithDetail);
		ObjectMapper mapper = new ObjectMapper();
		String jsonStr = mapper.writeValueAsString(customerWithDetail);
		RequestBuilder request = MockMvcRequestBuilders.put("/apiv1/customers")
				.content(jsonStr).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON);
		mockMvc.perform(request).andExpect(status().is4xxClientError()).andReturn();
	}

	@Test
	public void testDeleteCustomer_success() throws Exception {
		doAnswer(i -> null).when(customerServiceMock).deleteById(1);
		RequestBuilder request = MockMvcRequestBuilders.delete("/apiv1/customers/1").accept(MediaType.APPLICATION_JSON);
		mockMvc.perform(request).andExpect(status().isOk()).andReturn();
	}

	@Test
	public void testDeleteCustomer_failure() throws Exception {
		doThrow(RuntimeException.class).when(customerServiceMock).deleteById(1);
		RequestBuilder request = MockMvcRequestBuilders.delete("/apiv1/customers/1").accept(MediaType.APPLICATION_JSON);
		mockMvc.perform(request).andExpect(status().is5xxServerError()).andReturn();
	}
}
