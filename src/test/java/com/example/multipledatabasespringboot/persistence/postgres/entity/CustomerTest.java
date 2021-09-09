package com.example.multipledatabasespringboot.persistence.postgres.entity;

import com.example.multipledatabasespringboot.persistence.mysql.entity.User;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Timestamp;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Project title: multiple-database-spring-boot
 *
 * @author johnadeshola
 * Date: 9/9/21
 * Time: 10:02 AM
 */
@SpringBootTest
class CustomerTest {

    private final Faker faker = new Faker();
    private Customer customer;

    @BeforeEach
    public void setUp() {
        customer = Customer.builder().build();
    }

    @Test
    @DisplayName("User Entity Test")
    void testUserEntity() {
        customer = Customer.builder()
                .id(1L)
                .username("timadeshola")
                .customerCode(UUID.randomUUID().toString())
                .dateCreated(new Timestamp(System.currentTimeMillis()))
                .dateUpdated(new Timestamp(System.currentTimeMillis()))
                .build();

        assertNotNull(customer);
        assertEquals("timadeshola", customer.getUsername());
        assertThat(customer).isExactlyInstanceOf(User.class);
    }
}