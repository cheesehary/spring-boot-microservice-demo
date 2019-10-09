package com.demo.customerservice.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.demo.customerservice.dao.CustomerDAO;
import com.demo.customerservice.dao.CustomerExtDAO;
import com.demo.customerservice.entity.Customer;
import com.demo.customerservice.entity.CustomerExt;
import com.demo.customerservice.model.CustomerWithDetail;

@Service
public class CustomerServiceImpl implements CustomerService {
	@Autowired
	private CustomerDAO customerDAO;

	@Autowired
	private CustomerExtDAO customerExtDAO;

	private List<String> extProps = CustomerExtProp.getExtProps();

	@Override
	@Transactional
	public List<Customer> findAll(int page, int size) {
		return customerDAO.findAll(page, size);
	}

	@Override
	@Transactional
	public CustomerWithDetail findById(int id) {
		Customer customer = this.checkIfCustomerExists(id);
		List<CustomerExt> customerExts = customerExtDAO.findByCustomerId(id);
		return this.parseCustomerWithDetail(customer, customerExts);
	}

	@Override
	@Transactional
	public CustomerWithDetail save(CustomerWithDetail customerWithDetail) {
		Customer customer = this.extractCustomer(customerWithDetail, null);
		customer.setId(0);
		customerDAO.save(customer);
		List<CustomerExt> customerExts = this.extractExts(customer, customerWithDetail);
		customerExtDAO.saveMany(customerExts);
		return this.parseCustomerWithDetail(customer, customerExts);
	}

	@Override
	@Transactional
	public CustomerWithDetail update(CustomerWithDetail customerWithDetail) {
		Customer exsitedCustomer = this.checkIfCustomerExists(customerWithDetail.getId());
		Customer customer = this.extractCustomer(customerWithDetail, exsitedCustomer);
		customerDAO.save(customer);
		List<CustomerExt> customerExts = this.extractExts(customer, customerWithDetail);
		customerExtDAO.saveMany(customerExts);
		return this.parseCustomerWithDetail(customer, customerExts);
	}

	@Override
	@Transactional
	public void deleteById(int id) {
		this.checkIfCustomerExists(id);
		customerExtDAO.deleteByCustomerId(id);
		customerDAO.deleteById(id);
	}

	private Customer checkIfCustomerExists(int id) {
		Customer customer = customerDAO.findById(id);
		if (customer == null) {
			throw new CustomerNotFoundException("customer not found with id: " + id);
		} else {
			return customer;
		}
	}

	private Customer extractCustomer(CustomerWithDetail customerWithDetail, Customer customer) {
		if (customer == null) {
			customer = new Customer();
		}
		customer.setId(customerWithDetail.getId());
		customer.setFirstName(customerWithDetail.getFirstName());
		customer.setLastName(customerWithDetail.getLastName());
		customer.setDob(customerWithDetail.getDob());
		customer.setReserve(customerWithDetail.getReserve());
		return customer;
	}

	private List<CustomerExt> extractExts(Customer customer, CustomerWithDetail customerWithDetail) {
		List<CustomerExt> customerExts = this.extProps.stream().map(prop -> {
			switch (prop) {
			case CustomerExtProp.homeAddress:
				return new CustomerExt(customer.getId(), prop, customerWithDetail.getHomeAddress());
			default:
				return null;
			}
		}).collect(Collectors.toList());
		return customerExts;
	}

	private CustomerWithDetail parseCustomerWithDetail(Customer customer, List<CustomerExt> customerExts) {
		CustomerWithDetail customerWithDetail = new CustomerWithDetail();
		customerWithDetail.setId(customer.getId());
		customerWithDetail.setFirstName(customer.getFirstName());
		customerWithDetail.setLastName(customer.getLastName());
		customerWithDetail.setDob(customer.getDob());
		customerWithDetail.setReserve(customer.getReserve());
		customerExts.stream().forEach(ext -> {
			switch (ext.getProp()) {
			case CustomerExtProp.homeAddress:
				customerWithDetail.setHomeAddress(ext.getValue());
				break;
			}
		});
		return customerWithDetail;
	}

}
