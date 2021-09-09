package com.example.multipledatabasespringboot.model.request;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Project title: multiple-database-spring-boot
 *
 * @author johnadeshola
 * Date: 9/9/21
 * Time: 9:57 AM
 */
@SpringBootTest
public class UserRequestTest {

    private final Faker faker = new Faker();
    private UserRequest request;

    @BeforeEach
    public void setUp() {
        request = UserRequest.builder().build();
    }

    @Test
    @DisplayName("User Request Model Test")
    void testUserRequestModel() {
        request = UserRequest.builder()
                .username("timadeshola")
                .firstName(faker.name().firstName())
                .lastName(faker.name().lastName())
                .email(faker.internet().emailAddress())
                .build();

        assertNotNull(request);
        assertEquals("timadeshola", request.getUsername());
        assertThat(request).isExactlyInstanceOf(UserRequest.class);
    }
}