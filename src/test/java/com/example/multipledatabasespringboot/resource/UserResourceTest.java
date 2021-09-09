package com.example.multipledatabasespringboot.resource;

import com.example.multipledatabasespringboot.core.AppUtils;
import com.example.multipledatabasespringboot.model.request.UserRequest;
import com.example.multipledatabasespringboot.service.UserService;
import com.github.javafaker.Faker;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static com.example.multipledatabasespringboot.core.AppConstant.SUCCESS;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Project title: multiple-database-spring-boot
 *
 * @author johnadeshola
 * Date: 9/9/21
 * Time: 10:15 AM
 */
@WebMvcTest(UserResource.class)
class UserResourceTest {

    private final Faker faker = new Faker();

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    private UserRequest request;

    @BeforeEach
    public void setUp() {
        request = UserRequest.builder().build();
    }

    @SneakyThrows
    @Test
    @DisplayName("Create User Endpoint - Test")
    void testCreateUserEndpoint() {
        request = UserRequest.builder()
                .username("timadeshola")
                .firstName(faker.name().firstName())
                .lastName(faker.name().lastName())
                .email(faker.internet().emailAddress())
                .build();

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(AppUtils.toJson(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(SUCCESS))
                .andExpect(jsonPath("$.status").value("CREATED"));
    }

    @SneakyThrows
    @Test
    @DisplayName(value = "Update User Endpoint - Test")
    void testUpdateUserEndpoint() {
        request = UserRequest.builder()
                .username("timadeshola")
                .firstName(faker.name().firstName())
                .lastName(faker.name().lastName())
                .email(faker.internet().emailAddress())
                .build();

        mockMvc.perform(put("/users/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(AppUtils.toJson(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(SUCCESS))
                .andExpect(jsonPath("$.status").value("OK"));
    }

    @SneakyThrows
    @Test
    @DisplayName(value = "Delete User Endpoint - Test")
    void testDeleteUserEndpoint() {
        mockMvc.perform(delete("/users/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").value(false));

    }

    @SneakyThrows
    @Test
    @DisplayName(value = "Fetch User by ID Endpoint - Test")
    void testFetchUserByIdEndpoint() {
        mockMvc.perform(get("/users/fetchById/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(SUCCESS));

    }

    @SneakyThrows
    @Test
    @DisplayName(value = "Fetch User by Username Endpoint - Test")
    void testFetchUserByUsernameEndpoint() {
        mockMvc.perform(get("/users/fetchByUsername/{username}", "timadeshola")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(SUCCESS));
    }

    @SneakyThrows
    @Test
    @DisplayName(value = "Fetch All User Endpoint - Test")
    void testFetchUserByUsersEndpoint() {
        mockMvc.perform(get("/users/viewAll")
                        .queryParam("start", "0")
                        .queryParam("limit", "10")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(SUCCESS));
    }

}