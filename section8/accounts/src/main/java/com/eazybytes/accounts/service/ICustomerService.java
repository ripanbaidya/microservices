package com.eazybytes.accounts.service;

import com.eazybytes.accounts.dto.CustomerDetailsDto;

public interface ICustomerService {

    public CustomerDetailsDto fetchCustomerDetails(String mobileNumber);
}
