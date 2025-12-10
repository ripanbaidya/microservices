package com.example.accounts.repository;

import com.example.accounts.entity.Accounts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Accounts, Long> {

    // Find a customer account by customer id
    Optional<Accounts> findByCustomerId(Long customerId);

    /**
     * Deletes an account by customer ID.
     * <p>
     * Note: The {@code @Modifying} and {@code @Transactional} annotations are required for update/delete operations
     * when using custom JPQL or native SQL queries.
     * However, for Spring Data JPA derived query methods, these annotations are optional as the framework
     * automatically handles the transaction management and query execution.
     * </p>
     */
    // @Transactional
    // @Modifying
    void deleteByCustomerId(Long customerId);
}
