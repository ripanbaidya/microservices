package com.example.accounts.mapper;

import com.example.accounts.dto.AccountDto;
import com.example.accounts.entity.Accounts;

// Mapper class for converting between Account entity and AccountDto.
public class AccountsMapper {

    public static AccountDto mapToAccountDto(Accounts accounts, AccountDto accountDto) {
        accountDto.setAccountNumber(accounts.getAccountNumber());
        accountDto.setAccountType(accounts.getAccountType());
        accountDto.setBranchAddress(accounts.getBranchAddress());
        return accountDto;
    }

    public static Accounts mapToAccount(AccountDto accountDto, Accounts accounts) {
        accounts.setAccountNumber(accountDto.getAccountNumber());
        accounts.setAccountType(accountDto.getAccountType());
        accounts.setBranchAddress(accountDto.getBranchAddress());
        return accounts;
    }
}