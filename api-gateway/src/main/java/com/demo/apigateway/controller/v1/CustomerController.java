package com.demo.apigateway.controller.v1;

import java.util.Collections;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;

import com.demo.apigateway.model.CustomerList;
import com.demo.apigateway.model.CustomerWithDetail;
import com.demo.apigateway.model.ErrorResponse;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/apiv1/customers")
public class CustomerController {
	@Autowired
	private WebClient.Builder webClientBuilder;

	@GetMapping("")
	@HystrixCommand(fallbackMethod = "findAllCustomersFallback")
	public Object findAllCustomers(@RequestParam("page") Optional<Integer> pageParam,
			@RequestParam("size") Optional<Integer> sizeParam) {
		String url = (pageParam.isPresent() && sizeParam.isPresent())
				? "/apiv1/customers?page=" + pageParam.get() + "&size=" + sizeParam.get()
				: "/apiv1/customers";
		System.out.println(url);
		return webClientBuilder.build().get().uri(url).exchange()
				.flatMap(response -> this.handleResponse(response, CustomerList.class)).block();
	}

	@GetMapping("/{customerId}")
	@HystrixCommand(fallbackMethod = "findCustomerFallback")
	public Object findCustomer(@PathVariable int customerId) {
		return webClientBuilder.build().get().uri("/apiv1/customers/{customerId}", customerId).exchange()
				.flatMap(response -> this.handleResponse(response, CustomerWithDetail.class)).block();
	}

	@PostMapping("")
	public Object createCustomer(@RequestBody CustomerWithDetail customerWithDetail) {
		return webClientBuilder.build().post().uri("/apiv1/customers")
				.body(BodyInserters.fromObject(customerWithDetail)).exchange()
				.flatMap(response -> this.handleResponse(response, CustomerWithDetail.class)).block();
	}

	@PutMapping("")
	public Object updateCustomer(@RequestBody CustomerWithDetail customerWithDetail) {
		return webClientBuilder.build().put().uri("/apiv1/customers").body(BodyInserters.fromObject(customerWithDetail))
				.exchange().flatMap(response -> this.handleResponse(response, CustomerWithDetail.class)).block();
	}

	@DeleteMapping("/{customerId}")
	public Object DeleteCustomer(@PathVariable int customerId) {
		return webClientBuilder.build().delete().uri("/apiv1/customers/{customerId}", customerId).exchange()
				.flatMap(response -> this.handleResponse(response, Void.class)).block();
	}

	public Object findAllCustomersFallback(Optional<Integer> pageParam, Optional<Integer> sizeParam) {
		return new CustomerList(Collections.emptyList());
	}

	public Object findCustomerFallback(int customerId) {
		return new CustomerWithDetail();
	}

	private Mono<?> handleResponse(ClientResponse response, Class<?> className) {
		if (response.statusCode().isError()) {
			return response.bodyToMono(ErrorResponse.class);
		}
		return response.bodyToMono(className);
	}

}
