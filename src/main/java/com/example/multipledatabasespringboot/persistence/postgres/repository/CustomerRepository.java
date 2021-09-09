package com.example.multipledatabasespringboot.persistence.postgres.repository;

import com.example.multipledatabasespringboot.persistence.postgres.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Project title: multiple-database-spring-boot
 *
 * @author johnadeshola
 * Date: 9/9/21
 * Time: 8:24 AM
 */
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Optional<Customer> findByUsername(String username);

    Optional<Customer> findByCustomerCode(String customerCode);
}