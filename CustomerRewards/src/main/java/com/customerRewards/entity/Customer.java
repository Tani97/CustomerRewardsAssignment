package com.customerRewards.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Customer {

	@Id
	private Integer customerId;
	private String customerName;
	private String emailId;
	
	@OneToMany(cascade = CascadeType.ALL , fetch = FetchType.LAZY, mappedBy="customer")
	private List<Transaction> transactions = new ArrayList<>();


	public Customer() {
		super();
	}

	public Customer(Integer customerId, String customerName, String emailId, List<Transaction> transactions) {
		this.customerId = customerId;
		this.customerName = customerName;
		this.emailId = emailId;
		this.transactions = transactions;
	}

	public Integer getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public List<Transaction> getTransactions() {
		return transactions;
	}

	public void setTransactions(List<Transaction> transactions) {
		this.transactions = transactions;
	}

	@Override
	public String toString() {
		return "Customer [customerId=" + customerId + ", customerName=" + customerName + ", emailId=" + emailId
				+ ", transactions=" + transactions + "]";
	}
	
	
	
}
