package com.demo.customerservice.controller.v1;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.customerservice.entity.Customer;
import com.demo.customerservice.model.CustomerList;
import com.demo.customerservice.model.CustomerWithDetail;
import com.demo.customerservice.service.CustomerService;

@RestController
@RequestMapping("/apiv1/customers")
public class CustomerController {
	@Autowired
	private CustomerService customerService;

	@GetMapping("")
	public CustomerList findAllCustomers() {
		List<Customer> list = customerService.findAll();
		return new CustomerList(list);
	}

	@GetMapping("/{customerId}")
	public CustomerWithDetail findCustomer(@PathVariable int customerId) {
		return customerService.findById(customerId);
	}

	@PostMapping("")
	public CustomerWithDetail createCustomer(@Valid @RequestBody CustomerWithDetail customerWithDetail) {
		return customerService.save(customerWithDetail);
	}

	@PutMapping("")
	public CustomerWithDetail updateCustomer(@Valid @RequestBody CustomerWithDetail customerWithDetail) {
		return customerService.update(customerWithDetail);
	}

	@DeleteMapping("/{customerId}")
	public void deleteCustomer(@PathVariable int customerId) {
		customerService.deleteById(customerId);
	}
}
