package com.demo.customerservice.service;

import java.util.List;

import com.demo.customerservice.entity.Customer;
import com.demo.customerservice.model.CustomerWithDetail;

public interface CustomerService {
	public List<Customer> findAll();

	public CustomerWithDetail findById(int id);

	public CustomerWithDetail save(CustomerWithDetail customerWithDetail);

	public CustomerWithDetail update(CustomerWithDetail customerWithDetail);

	public void deleteById(int id);
}
