package com.demo.customerservice.service;

import java.util.Arrays;
import java.util.List;

public class CustomerExtProp {

	public static final String homeAddress = "homeAddress";

	public static final String emailAddress = "emailAddress";

	public static final List<String> getExtProps() {
		return Arrays.asList(homeAddress, emailAddress);
	}

}
