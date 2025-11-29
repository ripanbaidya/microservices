package com.example.accounts.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "accounts")
@Getter @Setter @NoArgsConstructor
public class Account extends BaseEntity {

    // Common field for Customer and Account entity
    @Column(name = "customer_id")
    private Long customerId;

    // Note: We are not using any auto generated ID strategy here.
    // We will write custome logic to generte account number(ID)
    @Id
    @Column(name = "account_number")
    private Long accountNumber;

    @Column(name = "account_type")
    private String accountType;

    @Column(name = "branch_address")
    private String branchAddress;
}
