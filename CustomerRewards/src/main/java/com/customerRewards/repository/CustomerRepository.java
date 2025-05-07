package com.customerRewards.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.customerRewards.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer,Integer>{

}
