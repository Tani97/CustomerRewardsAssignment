package com.customerRewards.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.customerRewards.Exception.CustomerNotFoundException;
import com.customerRewards.dto.CustomerRewards;
import com.customerRewards.entity.Customer;
import com.customerRewards.entity.Transaction;
import com.customerRewards.repository.CustomerRepository;

public class CustomerServiceTest {

	@Before
	public void setup() {
		MockitoAnnotations.openMocks(this);
	}
	
	@InjectMocks
	private CustomerService customerService;
	
	@Mock
	private CustomerRepository customerRepository;
	
	@Test
    public void saveCustomerTest() {
		
		Customer customer1 = new Customer();
        customer1.setCustomerId(123);
        customer1.setCustomerName("Taniya");

        Transaction tx1 = new Transaction();
        tx1.setAmount(120.0); //90 points
        tx1.setDate(LocalDate.of(2025, 02, 15));

        
        Transaction tx2 = new Transaction();
        tx2.setAmount(80.0); // 0 points
        tx2.setDate(LocalDate.of(2025, 02, 27));
        
        customer1.setTransactions(List.of(tx1,tx2));
        
        when(customerRepository.findById(123)).thenReturn(Optional.of(customer1));
        when(customerRepository.save(any(Customer.class))).thenReturn(customer1);
        assertNotNull(customerService.saveCustomer(customer1));
        
        CustomerRewards rewards = customerService.getCustomerDetails(123);
        assertEquals(90, rewards.getTotalPoints());
        assertTrue(rewards.getMonthlyPoints().containsKey("FEBRUARY,2025"));
        
        //customer with no transactions -
        Customer customer2 = new Customer();
        customer2.setCustomerId(111);
        customer2.setCustomerName("Peter");
        
        when(customerRepository.findById(111)).thenReturn(Optional.of(customer2));
        when(customerRepository.save(any(Customer.class))).thenReturn(customer2);
        CustomerRewards rewards2 = customerService.getCustomerDetails(111);
        customer2.setTransactions(Collections.emptyList());
        assertEquals(0, rewards2.getTotalPoints());
        
    }


    @Test
    public void customerNotFoundTest() {
    	
        when(customerRepository.findById(any())).thenReturn(Optional.empty());
        when(customerRepository.findById(any())).thenThrow(new CustomerNotFoundException("Test"));
        
    }
	
	
}
