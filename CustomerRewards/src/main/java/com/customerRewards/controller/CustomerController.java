package com.customerRewards.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.customerRewards.dto.CustomerRewards;
import com.customerRewards.entity.Customer;
import com.customerRewards.service.CustomerService;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

	@Autowired
	private CustomerService customerService;

	//Save customer details into DB
    @PostMapping
    public ResponseEntity<Customer> addCustomer(@RequestBody Customer customer) {

    	try {
            Customer savedCustomer = customerService.saveCustomer(customer);
            return new ResponseEntity<>(savedCustomer, HttpStatus.CREATED); 
        } catch (Exception e) {
        	return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    	
     }

    //Retrieve customer details along with reward points
    @GetMapping("/{customerId}")
    public ResponseEntity<CustomerRewards> getRewards(@PathVariable Integer customerId) {
    	CustomerRewards response = customerService.getCustomerDetails(customerId);
    	return ResponseEntity.ok(response);
    	
    }
    	
}
