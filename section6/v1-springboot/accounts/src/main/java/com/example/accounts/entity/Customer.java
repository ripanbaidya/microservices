package com.example.accounts.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "customers")
@Getter @Setter @NoArgsConstructor
public class Customer extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id")
    private Long customerId;

    @Column(name = "name", length = 30)
    private String name;

    @Column(name = "email", length = 50)
    private String email;

    @Column(name = "mobile_number", unique = true, length = 15)
    private String mobileNumber;
}
