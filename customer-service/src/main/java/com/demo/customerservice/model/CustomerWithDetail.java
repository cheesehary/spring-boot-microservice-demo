package com.demo.customerservice.model;

import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class CustomerWithDetail {
	private int id;

	@NotNull
	@Size(min = 1, max = 14, message = "length should be between 1 and 14")
	private String firstName;

	@NotNull
	@Size(min = 1, max = 14, message = "length should be between 1 and 14")
	private String lastName;

	private Date dob;

	private String reserve;

	private String homeAddress;

	@Pattern(regexp = "[a-zA-Z0-9_.]+@[a-zA-Z0-9]+[.]{1}[a-zA-Z]{2,4}", message = "invalid email address")
	private String emailAddress;

	public CustomerWithDetail() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public String getReserve() {
		return reserve;
	}

	public void setReserve(String reserve) {
		this.reserve = reserve;
	}

	public String getHomeAddress() {
		return homeAddress;
	}

	public void setHomeAddress(String homeAddress) {
		this.homeAddress = homeAddress;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}
}
