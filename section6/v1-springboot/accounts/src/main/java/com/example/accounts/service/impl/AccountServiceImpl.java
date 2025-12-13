package com.example.accounts.service.impl;

import com.example.accounts.constants.AccountConstants;
import com.example.accounts.dto.AccountDto;
import com.example.accounts.dto.CustomerDto;
import com.example.accounts.entity.Accounts;
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
import org.springframework.transaction.annotation.Transactional;

import java.util.Random;

@Service
@Slf4j @RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    // With lombok we dont need to write any Constructors or Setter to add depencencies,
    // @RequiredArgsConstructor will automatically inject the dependencies for all final fields(classs/ interfaces)
    private final AccountRepository accountRepository;

    private final CustomerRepository customerRepository;

    @Override
    public void createAccount(CustomerDto customerDto) {
        // Check if a customer already exists by his/her mobile number
        customerRepository.findByMobileNumber(customerDto.getMobileNumber()).ifPresent(existingCustomer -> {
            log.error("Customer with mobile number {} already exists", customerDto.getMobileNumber());
            throw new CustomerAlreadyExistException("Customer already exist with mobile number: "
                    + customerDto.getMobileNumber());
        });

        // Convert customerDto to customer entity with the help of mapper
        Customer customer = CustomerMapper.mapToCustomer(customerDto, new Customer());

        // TODO: Implement JPA Auditing to automatically populate createdAt, createdBy, updatedAt, and updatedBy fields
        // customer.setCreatedAt(LocalDateTime.now());
        // customer.setCreatedBy("Anonymous");

        // Save customer entity to a database
        Customer savedCustomer = customerRepository.save(customer);

        // Create an account entity and set customer id
        accountRepository.save(createNewAccount(savedCustomer));
    }

    @Override
    public CustomerDto getCustomerDetailsByMobileNumber(String mobileNumber) {
        Customer existingCustomer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Customer", "mobile number", mobileNumber)
        );
        // We want to return account information along with customer details
        Accounts accounts = accountRepository.findByCustomerId(existingCustomer.getCustomerId()).orElseThrow(
                () -> new ResourceNotFoundException("Account", "customer id", existingCustomer.getCustomerId().toString())
        );

        // convert customer to customer dto and set account information while converting the account
        // entity to account dto
        CustomerDto customerDto = CustomerMapper.mapToCustomerDto(existingCustomer, new CustomerDto());
        customerDto.setAccount(AccountsMapper.mapToAccountDto(accounts, new AccountDto()));

        // return customer dto as response
        return customerDto;
    }

    @Override
    public boolean updateCustomerAndAccountDetails(CustomerDto customerDto) {
        boolean isUpdated = false;
        AccountDto accountDto = customerDto.getAccount();
        if (accountDto != null) {
            // find the account entity by account number
            Accounts accounts = accountRepository.findById(accountDto.getAccountNumber()).orElseThrow(
                    () -> new ResourceNotFoundException("Account", "AccountNumber", accountDto.getAccountNumber().toString())
            );
            // udpate the account info if found
            AccountsMapper.mapToAccount(accountDto, accounts);
            accounts = accountRepository.save(accounts);

            Long customerId = accounts.getCustomerId();
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
    @Transactional
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
    private Accounts createNewAccount(Customer customer) {
        Accounts accounts = new Accounts();

        accounts.setCustomerId(customer.getCustomerId());

        // Generate a random 10 digit account number, This will work as primary key for account entity
        long randomAccountNumber = 1000000000L + new Random().nextLong(900000000L);
        accounts.setAccountNumber(randomAccountNumber);

        accounts.setAccountType(AccountConstants.SAVINGS);
        accounts.setBranchAddress(AccountConstants.ADDRESS);

        // TODO: Implement JPA Auditing to automatically populate createdAt, createdBy, updatedAt, and updatedBy fields
        // account.setCreatedAt(LocalDateTime.now());
        // account.setCreatedBy("Anonymous");

        return accounts;
    }
}
