package com.example.multipledatabasespringboot.persistence.postgres.repository;

import com.example.multipledatabasespringboot.config.CustomDatabaseBean;
import com.example.multipledatabasespringboot.core.exceptions.CustomException;
import com.example.multipledatabasespringboot.persistence.mysql.entity.User;
import com.example.multipledatabasespringboot.persistence.postgres.entity.Customer;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;

import java.sql.Timestamp;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Project title: multiple-database-spring-boot
 *
 * @author johnadeshola
 * Date: 9/9/21
 * Time: 10:03 AM
 */
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@Profile("test")
public class CustomerRepositoryTest {

    private final Faker faker = new Faker();
    @Autowired
    private CustomDatabaseBean repository;
    private Customer customer;

    @BeforeEach
    public void setUp() {
        customer = Customer.builder().build();
    }

    @Test
    @DisplayName("Save Customer - Repository Test")
    void testSaveUser() {
        customer = Customer.builder()
                .id(1L)
                .username("timadeshola")
                .customerCode(UUID.randomUUID().toString())
                .dateCreated(new Timestamp(System.currentTimeMillis()))
                .dateUpdated(new Timestamp(System.currentTimeMillis()))
                .build();

        customer = repository.customerRepositoryEm().save(customer);

        Customer foundUser = repository.customerRepositoryEm().findByUsername(customer.getUsername()).<CustomException>orElseThrow(() -> {
            throw new CustomException("Customer info cannot be found", HttpStatus.NOT_FOUND);
        });

        assertThat(customer.getUsername()).isNotNull();
        assertNotNull(customer);
        assertNotNull(foundUser);
        assertThat(foundUser.getUsername()).isEqualTo(customer.getUsername());
        assertThat(foundUser).isExactlyInstanceOf(User.class);
    }


    @Test
    @DisplayName("Fetch Customer - Repository Test")
    void testFetchUserByEmail() {
        customer = Customer.builder()
                .id(1L)
                .username("timadeshola")
                .customerCode(UUID.randomUUID().toString())
                .dateCreated(new Timestamp(System.currentTimeMillis()))
                .dateUpdated(new Timestamp(System.currentTimeMillis()))
                .build();

        customer = repository.customerRepositoryEm().save(customer);

        Customer foundUser = repository.customerRepositoryEm().findByCustomerCode(customer.getCustomerCode()).<CustomException>orElseThrow(() -> {
            throw new CustomException("Customer info cannot be found", HttpStatus.NOT_FOUND);
        });

        assertThat(customer.getCustomerCode()).isNotNull();
        assertNotNull(customer);
        assertNotNull(foundUser);
        assertThat(foundUser.getUsername()).isEqualTo(customer.getUsername());
        assertThat(foundUser).isExactlyInstanceOf(User.class);
    }

}