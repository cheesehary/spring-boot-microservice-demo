package com.demo.apigateway.controller.v1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import com.demo.apigateway.model.CustomerList;
import com.demo.apigateway.model.CustomerWithDetail;
import com.demo.apigateway.model.ErrorResponse;

@RestController
@RequestMapping("/apiv1/customers")
public class CustomerController {
	@Autowired
	private WebClient.Builder webClientBuilder;

	@GetMapping("")
	public Object findAllCustomers() {
		return webClientBuilder.build().get().uri("/apiv1/customers")
				.exchange().flatMap(response -> {
					if (response.statusCode().isError()) {
						return response.bodyToMono(ErrorResponse.class);
					} else {
						return response.bodyToMono(CustomerList.class);
					}
				});
	}

	@GetMapping("/{customerId}")
	public Object findCustomer(@PathVariable int customerId) {
		return webClientBuilder.build().get()
				.uri("/apiv1/customers/{customerId}", customerId).exchange()
				.flatMap(response -> {
					if (response.statusCode().isError()) {
						return response.bodyToMono(ErrorResponse.class);
					} else {
						return response.bodyToMono(CustomerWithDetail.class);
					}
				});
	}

	@PostMapping("")
	public Object createCustomer(@RequestBody CustomerWithDetail customerWithDetail) {
		return webClientBuilder.build().post().uri("/apiv1/customers")
				.body(BodyInserters.fromObject(customerWithDetail)).exchange().flatMap(response -> {
					if (response.statusCode().isError()) {
						return response.bodyToMono(ErrorResponse.class);
					} else {
						return response.bodyToMono(CustomerWithDetail.class);
					}
				});
	}

	@PutMapping("")
	public Object updateCustomer(@RequestBody CustomerWithDetail customerWithDetail) {
		return webClientBuilder.build().put().uri("/apiv1/customers")
				.body(BodyInserters.fromObject(customerWithDetail)).exchange().flatMap(response -> {
					if (response.statusCode().isError()) {
						return response.bodyToMono(ErrorResponse.class);
					} else {
						return response.bodyToMono(CustomerWithDetail.class);
					}
				});
	}

	@DeleteMapping("/{customerId}")
	public void DeleteCustomer(@PathVariable int customerId) {
		webClientBuilder.build().delete()
				.uri("/apiv1/customers/{customerId}", customerId).exchange()
				.flatMap(response -> {
					if (response.statusCode().isError()) {
						return response.bodyToMono(ErrorResponse.class);
					} else {
						return response.bodyToMono(Void.class);
					}
				});
	}

}
