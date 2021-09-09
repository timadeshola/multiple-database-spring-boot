package com.example.multipledatabasespringboot.service;

import com.example.multipledatabasespringboot.config.CustomDatabaseBean;
import com.example.multipledatabasespringboot.core.ModelMapperUtils;
import com.example.multipledatabasespringboot.model.request.UserRequest;
import com.example.multipledatabasespringboot.model.response.PaginateResponse;
import com.example.multipledatabasespringboot.model.response.UserResponse;
import com.example.multipledatabasespringboot.persistence.mysql.entity.User;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Project title: multiple-database-spring-boot
 *
 * @author johnadeshola
 * Date: 9/9/21
 * Time: 10:07 AM
 */
@SpringBootTest
class UserServiceTest {

    private final Faker faker = new Faker();
    @Autowired
    private UserService userService;
    @Autowired
    private CustomDatabaseBean repository;
    private User user;
    private UserRequest request;
    private UserResponse response;

    @BeforeEach
    public void setUp() {
        user = User.builder().build();
        request = UserRequest.builder().build();
        response = UserResponse.builder().build();
    }

    @Test
    @DisplayName("Create User Service Test")
    void testCreateUser() {
        request = UserRequest.builder()
                .username(faker.name().username())
                .firstName(faker.name().firstName())
                .lastName(faker.name().lastName())
                .email(faker.internet().emailAddress())
                .build();

        response = userService.createUser(request);

        user = ModelMapperUtils.map(response, user);

        assertThat(user.getEmail()).isEqualTo(response.getEmail());
        assertNotNull(user);
        assertNotNull(response);
        assertEquals(request.getUsername(), response.getUsername());
        assertEquals(user.getUsername(), response.getUsername());
        assertThat(response).isInstanceOf(UserResponse.class);
    }

    @Test
    @DisplayName("Update User Service Test")
    void testUpdateUser() {
        request = UserRequest.builder()
                .username(faker.name().username())
                .firstName(faker.name().firstName())
                .lastName(faker.name().lastName())
                .email(faker.internet().emailAddress())
                .build();

        response = userService.createUser(request);

        request.setEmail("timadeshola@gmail.com");
        request.setLastName("Adeshola");
        response = userService.updateUser(request, response.getId());

        user = ModelMapperUtils.map(response, user);

        assertEquals("timadeshola@gmail.com", user.getEmail());
        assertEquals("Adeshola", user.getLastName());
        assertThat(user.getEmail()).isEqualTo(response.getEmail());
        assertNotNull(user);
        assertNotNull(response);
        assertEquals(request.getUsername(), response.getUsername());
        assertThat(response).isInstanceOf(UserResponse.class);
    }

    @Test
    @DisplayName("Delete User Service Test")
    void testDeleteUser() {
        request = UserRequest.builder()
                .username(faker.name().username())
                .firstName(faker.name().firstName())
                .lastName(faker.name().lastName())
                .email(faker.internet().emailAddress())
                .build();

        response = userService.createUser(request);

        Boolean deleteUser = userService.deleteUser(response.getId());

        assertThat(deleteUser).isTrue();
    }

    @Test
    @DisplayName("Fetch User By ID - Service Test")
    void testFetchUserById() {
        request = UserRequest.builder()
                .username(faker.name().username())
                .firstName(faker.name().firstName())
                .lastName(faker.name().lastName())
                .email(faker.internet().emailAddress())
                .build();

        response = userService.createUser(request);

        UserResponse foundResponse = userService.fetchUser(response.getId());

        user = ModelMapperUtils.map(foundResponse, user);

        assertThat(response.getEmpId()).isEqualTo(foundResponse.getEmpId());
        assertThat(user.getEmail()).isEqualTo(response.getEmail());
        assertNotNull(user);
        assertNotNull(response);
        assertEquals(request.getUsername(), response.getUsername());
        assertThat(response).isInstanceOf(UserResponse.class);
        assertThat(user).isInstanceOf(User.class);
    }

    @Test
    @DisplayName("Fetch User By Username - Service Test")
    void testFetchUserByUsername() {
        request = UserRequest.builder()
                .username(faker.name().username())
                .firstName(faker.name().firstName())
                .lastName(faker.name().lastName())
                .email(faker.internet().emailAddress())
                .build();

        response = userService.createUser(request);

        UserResponse foundResponse = userService.fetchUser(response.getUsername());

        user = ModelMapperUtils.map(foundResponse, user);

        assertThat(response.getEmpId()).isEqualTo(foundResponse.getEmpId());
        assertThat(user.getEmail()).isEqualTo(response.getEmail());
        assertNotNull(user);
        assertNotNull(response);
        assertEquals(request.getUsername(), response.getUsername());
        assertThat(response).isInstanceOf(UserResponse.class);
        assertThat(user).isInstanceOf(User.class);
    }

    @Test
    @DisplayName("Fetch All Users - Service Test")
    void testFetchUsers() {
        List<UserRequest> requests = new ArrayList<>();

        for (int i = 1; i <= 20; i++) {
            request = UserRequest.builder()
                    .username(faker.name().username())
                    .firstName(faker.name().firstName())
                    .lastName(faker.name().lastName())
                    .email(faker.internet().emailAddress())
                    .build();
            requests.add(request);
        }

        List<User> users = ModelMapperUtils.mapAll(requests, User.class);

        List<User> userList = repository.userRepositoryEm().saveAll(users);

        PaginateResponse<UserResponse> fetchUsers = userService.fetchUsers(0, 20);


        assertTrue((long) userList.size() <= 20, () -> "User count is greater than 20");
        assertNotNull(users);
        assertNotNull(requests);
        assertNotNull(userList);
        assertNotNull(fetchUsers);
        assertThat(users).isInstanceOf(List.class);
        assertThat(fetchUsers.getContent()).isInstanceOf(List.class);
        assertThat(fetchUsers.getTotalElements()).isInstanceOf(Long.class);
        assertThat(fetchUsers.getContent().size()).isGreaterThanOrEqualTo(10);
    }
}