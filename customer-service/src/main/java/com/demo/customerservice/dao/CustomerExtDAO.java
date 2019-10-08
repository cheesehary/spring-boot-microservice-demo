package com.demo.customerservice.dao;

import java.util.List;

import com.demo.customerservice.entity.CustomerExt;

public interface CustomerExtDAO {
	public List<CustomerExt> findByCustomerId(int id);

	public CustomerExt save(CustomerExt customerExt);

	public List<CustomerExt> saveMany(List<CustomerExt> customerExts);

	public void deleteByCustomerId(int id);
}
