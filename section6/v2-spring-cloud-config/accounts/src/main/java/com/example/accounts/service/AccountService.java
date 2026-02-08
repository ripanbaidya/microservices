package com.example.accounts.service;

import com.example.accounts.dto.CustomerDto;

// account service interface for CRUD operations
public interface AccountService {

    // Create a new account for a customer
    void createAccount(CustomerDto request);

    // Fetch customer details by mobile number
    CustomerDto getCustomerDetailsByMobileNumber(String mobileNumber);

    // Update customer details along with account details (except account number)
    boolean updateCustomerAndAccountDetails(CustomerDto customerDto);

    // Delete a customer account
    boolean deleteAccount(String mobileNumber);
}
