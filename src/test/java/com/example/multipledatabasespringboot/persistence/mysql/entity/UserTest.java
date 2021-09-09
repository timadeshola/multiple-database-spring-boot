package com.example.multipledatabasespringboot.persistence.mysql.entity;

import com.example.multipledatabasespringboot.model.enums.Status;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;

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
 * Time: 10:00 AM
 */
@SpringBootTest
@Profile("test")
class UserTest {

    private final Faker faker = new Faker();
    private User user;

    @BeforeEach
    public void setUp() {
        user = User.builder().build();
    }

    @Test
    @DisplayName("User Entity Test")
    void testUserEntity() {
        user = User.builder()
                .id(1L)
                .username("timadeshola")
                .firstName(faker.name().firstName())
                .lastName(faker.name().lastName())
                .empId(UUID.randomUUID().toString())
                .status(Status.ACTIVE.getStatus())
                .email(faker.internet().emailAddress())
                .dateCreated(new Timestamp(System.currentTimeMillis()))
                .dateUpdated(new Timestamp(System.currentTimeMillis()))
                .build();

        assertNotNull(user);
        assertEquals("timadeshola", user.getUsername());
        assertThat(user).isExactlyInstanceOf(User.class);
    }
}