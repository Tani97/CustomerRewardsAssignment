package com.customerRewards.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.customerRewards.entity.Transaction;
import com.customerRewards.Exception.CustomerNotFoundException;
import com.customerRewards.dto.CustomerRewards;
import com.customerRewards.entity.Customer;
import com.customerRewards.repository.CustomerRepository;

@Service
public class CustomerService {
	
    @Autowired
    private CustomerRepository customerRepository;

    /**
     * @param customer : customer details along with transaction details
     * are saved to DB
     */
	public Customer saveCustomer(Customer customer) {
		
		if (customer.getTransactions() != null) {
            for(Transaction transaction : customer.getTransactions()) {
                transaction.setCustomer(customer);
            }
        }

        return customerRepository.save(customer);

	}

	/**
	 * @param customerId
	 * @return the customerRewards DTO after populating required details
	 */
	public CustomerRewards getCustomerDetails(Integer customerId) {
		
		Optional<Customer> customerOpt = customerRepository.findById(customerId);
        if (customerOpt.isEmpty()) {
            throw new CustomerNotFoundException("Customer with ID: "+ customerId +" not found");
        }

        Customer customer = customerOpt.get();
        List<Transaction> transactions = customer.getTransactions();

        Map<String, Integer> monthlyPoints = new HashMap<>();
        int totalPoints = 0;

        if (transactions != null && !transactions.isEmpty()) {
            for (Transaction t : transactions) {
                int points = calculatePoints(t.getAmount());
                String month = t.getDate().getMonth() + "," + t.getDate().getYear();
                monthlyPoints.merge(month, points, Integer::sum);
                totalPoints += points;
            }
        }

        CustomerRewards response = new CustomerRewards();
        response.setCustomerId(customer.getCustomerId());
        response.setCustomerName(customer.getCustomerName());
        response.setEmailId(customer.getEmailId());
        response.setMonthlyPoints(monthlyPoints);
        response.setTotalPoints(totalPoints);

        return response;
    }

	/**
	 * @param amount - transaction amount
	 * @return the reward points
	 */
    private int calculatePoints(Double amount) {
        if (amount == null)
        	return 0;
        int points = 0;
        //considering rewards points only applicable if transaction amount exceeds 100
        if (amount > 100) {
            points += (int) ((amount - 100) * 2) + 50;
        }
        return points;
    }
	

}
