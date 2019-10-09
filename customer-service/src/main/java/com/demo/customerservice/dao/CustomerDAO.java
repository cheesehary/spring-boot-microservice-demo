package com.demo.customerservice.dao;

import java.util.List;

import com.demo.customerservice.entity.Customer;

public interface CustomerDAO {
	public List<Customer> findAll(int page, int size);
	
	public Customer findById(int id);
	
	public Customer save(Customer customer);
	
	public void deleteById(int id);
}
