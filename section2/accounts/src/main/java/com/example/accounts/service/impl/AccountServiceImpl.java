package com.example.accounts.service.impl;

import com.example.accounts.constants.AccountConstants;
import com.example.accounts.dto.AccountDto;
import com.example.accounts.dto.CustomerDto;
import com.example.accounts.entity.Account;
import com.example.accounts.entity.Customer;
import com.example.accounts.exception.CustomerAlreadyExistException;
import com.example.accounts.exception.ResourceNotFoundException;
import com.example.accounts.mapper.AccountsMapper;
import com.example.accounts.mapper.CustomerMapper;
import com.example.accounts.repository.AccountRepository;
import com.example.accounts.repository.CustomerRepository;
import com.example.accounts.service.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

@Service
@Slf4j @RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    private final CustomerRepository customerRepository;

    @Override
    public void createAccount(CustomerDto customerDto) {
        // convert customerDto to customer entity with the help of mapper
        Customer customer = CustomerMapper.mapToCustomer(customerDto, new Customer());

        // check if a customer already exists by his/her mobile number
        customerRepository.findByMobileNumber(customer.getMobileNumber()).ifPresent(existingCustomer -> {
            log.error("Customer with mobile number {} already exists", customer.getMobileNumber());
            throw new CustomerAlreadyExistException("Customer already exist with mobile number: "
                    + customer.getMobileNumber());
        });

        // TODO: Implement JPA Auditing to automatically populate createdAt, createdBy, updatedAt, and updatedBy fields
        // customer.setCreatedAt(LocalDateTime.now());
        // customer.setCreatedBy("Anonymous");

        // save customer entity to a database
        Customer savedCustomer = customerRepository.save(customer);

        // create an account entity and set customer id
        accountRepository.save(createNewAccount(savedCustomer));


    }

    @Override
    public CustomerDto getCustomerDetailsByMobileNumber(String mobileNumber) {
        Customer existingCustomer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Customer", "mobile number", mobileNumber)
        );
        // We want to return account information along with customer details
        Account account = accountRepository.findByCustomerId(existingCustomer.getCustomerId()).orElseThrow(
                () -> new ResourceNotFoundException("Account", "customer id", existingCustomer.getCustomerId().toString())
        );

        // convert customer to customer dto and set account information while converting the account
        // entity to account dto
        CustomerDto customerDto = CustomerMapper.mapToCustomerDto(existingCustomer, new CustomerDto());
        customerDto.setAccount(AccountsMapper.mapToAccountDto(account, new AccountDto()));

        // return customer dto as response
        return customerDto;
    }

    @Override
    public boolean updateAccount(CustomerDto customerDto) {
        boolean isUpdated = false;
        AccountDto accountDto = customerDto.getAccount();
        if (accountDto != null) {
            // find the account entity by account number
            Account account = accountRepository.findById(accountDto.getAccountNumber()).orElseThrow(
                    () -> new ResourceNotFoundException("Account", "AccountNumber", accountDto.getAccountNumber().toString())
            );
            // udpate the account info if found
            AccountsMapper.mapToAccount(accountDto, account);
            account = accountRepository.save(account);

            Long customerId = account.getCustomerId();
            Customer customer = customerRepository.findById(customerId).orElseThrow(
                    () -> new ResourceNotFoundException("Customer", "CustomerID", customerId.toString())
            );
            CustomerMapper.mapToCustomer(customerDto,customer);
            customerRepository.save(customer);
            isUpdated = true;
        }

        return isUpdated;
    }

    @Override
    public boolean deleteAccount(String mobileNumber) {
        Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Customer", "mobile number", mobileNumber)
        );

        // delete account for the customer
        accountRepository.deleteByCustomerId(customer.getCustomerId());
        customerRepository.deleteById(customer.getCustomerId());

        return true;
    }


    // method to create a new account for customer
    private Account createNewAccount(Customer customer) {
        Account account = new Account();
        account.setCustomerId(customer.getCustomerId());

        // generate a random account number
        long randomAccountNumber = 1000000000L + new Random().nextLong(900000000L);
        account.setAccountNumber(randomAccountNumber);

        account.setAccountType(AccountConstants.SAVINGS);
        account.setBranchAddress(AccountConstants.ADDRESS);

        // TODO: Implement JPA Auditing to automatically populate createdAt, createdBy, updatedAt, and updatedBy fields
        // account.setCreatedAt(LocalDateTime.now());
        // account.setCreatedBy("Anonymous");

        return account;
    }
}
