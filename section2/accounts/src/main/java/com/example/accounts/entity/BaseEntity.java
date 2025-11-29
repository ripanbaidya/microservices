package com.example.accounts.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

// The @MappedSuperclass annotation indicates that this class is a superclass; it contains some common
// properties, which might be used by other entities in the application.
@MappedSuperclass
// Specifies the callback listener classes to be used for an entity or mapped superclass.
// This annotation may be applied to an entity class or mapped superclass.
@EntityListeners(AuditingEntityListener.class)
@Getter @Setter @ToString
public class BaseEntity {

    // Note: updatable = false
    // It tells JPA that once the value is set during the initial insert, it should not be modified during subsequent updates.
    // This is typically used for fields like createdAt and createdBy, which should remain constant
    @CreatedDate
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    // Declares a field as the one representing the principal that created the entity containing the field.
    // We need to use @EnableJpaAuditing annotation to enable this feature.
    @CreatedBy
    @Column(name = "created_by", updatable = false)
    private String createdBy;

    // Note: insertable = false
    // It tells JPA that this field should not be inserted during insert operations.
    // This is typically used for fields like updatedAt and updatedBy, which are updated by the system.
    @LastModifiedDate
    @Column(name = "updated_at", insertable = false)
    private LocalDateTime updatedAt;

    @LastModifiedBy
    @Column(name = "updated_by", insertable = false)
    private String updatedBy;

}
