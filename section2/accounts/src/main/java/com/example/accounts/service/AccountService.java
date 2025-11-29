package com.example.accounts.service;

import com.example.accounts.dto.CustomerDto;

// account service interface for CRUD operations
public interface AccountService {

    // create a new account for a customer
    void createAccount(CustomerDto request);

    // get customer details by mobile number
    CustomerDto getCustomerDetailsByMobileNumber(String mobileNumber);

    // update customer details along with account details (except account number)
    boolean updateAccount(CustomerDto customerDto);

    // delete customer account
    boolean deleteAccount(String mobileNumber);
}
