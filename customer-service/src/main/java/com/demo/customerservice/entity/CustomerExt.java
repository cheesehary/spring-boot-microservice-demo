package com.demo.customerservice.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="customer_ext")
public class CustomerExt {
	@Id
	@Column(name="id")
	private String id;
	
	@Column(name="customer_id")
	private int customerId;
	
	@Column(name="prop")
	private String prop;
	
	@Column(name="value")
	private String value;

	public CustomerExt() {
	}

	public CustomerExt(int customerId, String prop, String value) {
		this.id = String.valueOf(customerId) + prop;
		this.customerId = customerId;
		this.prop = prop;
		this.value = value;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	public String getProp() {
		return prop;
	}

	public void setProp(String prop) {
		this.prop = prop;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
