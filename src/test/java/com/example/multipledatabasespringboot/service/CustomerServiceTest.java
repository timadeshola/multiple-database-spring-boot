package com.example.multipledatabasespringboot.service;

import com.example.multipledatabasespringboot.config.CustomDatabaseBean;
import com.example.multipledatabasespringboot.core.ModelMapperUtils;
import com.example.multipledatabasespringboot.model.enums.Status;
import com.example.multipledatabasespringboot.model.request.CustomerRequest;
import com.example.multipledatabasespringboot.model.response.CustomerResponse;
import com.example.multipledatabasespringboot.model.response.PaginateResponse;
import com.example.multipledatabasespringboot.persistence.mysql.entity.User;
import com.example.multipledatabasespringboot.persistence.mysql.repository.UserRepository;
import com.example.multipledatabasespringboot.persistence.postgres.entity.Customer;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
class CustomerServiceTest {

    private final Faker faker = new Faker();
    @Autowired
    private CustomerService customerService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CustomDatabaseBean repository;
    private Customer customer;
    private User user;
    private CustomerRequest request;
    private CustomerResponse response;

    @BeforeEach
    public void setUp() {
        customer = Customer.builder().build();
        request = CustomerRequest.builder().build();
        response = CustomerResponse.builder().build();
        user = User.builder().build();
    }

    @Test
    @DisplayName("Create Customer Service Test")
    void testCreateUser() {

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

        userRepository.save(user);

        request = CustomerRequest.builder()
                .username(user.getUsername())
                .build();

        response = customerService.createCustomer(request);

        customer = ModelMapperUtils.map(response, customer);

        assertThat(customer.getCustomerCode()).isEqualTo(response.getCustomerCode());
        assertNotNull(customer);
        assertNotNull(response);
        assertEquals(request.getUsername(), response.getUsername());
        assertEquals(customer.getUsername(), response.getUsername());
        assertThat(response).isInstanceOf(CustomerResponse.class);
    }

    @Test
    @DisplayName("Fetch Customer By ID - Service Test")
    void testFetchUserById() {
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

        userRepository.save(user);

        request = CustomerRequest.builder()
                .username(user.getUsername())
                .build();

        response = customerService.createCustomer(request);

        CustomerResponse foundResponse = customerService.fetchCustomer(response.getId());

        customer = ModelMapperUtils.map(foundResponse, customer);

        assertThat(response.getEmpId()).isEqualTo(foundResponse.getEmpId());
        assertThat(customer.getCustomerCode()).isEqualTo(response.getCustomerCode());
        assertNotNull(customer);
        assertNotNull(response);
        assertEquals(request.getUsername(), response.getUsername());
        assertThat(response).isInstanceOf(CustomerResponse.class);
        assertThat(customer).isInstanceOf(Customer.class);
    }

    @Test
    @DisplayName("Fetch Customer By Username - Service Test")
    void testFetchUserByUsername() {

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

        userRepository.save(user);

        request = CustomerRequest.builder()
                .username(user.getUsername())
                .build();

        response = customerService.createCustomer(request);

        CustomerResponse foundResponse = customerService.fetchCustomer(response.getUsername());

        customer = ModelMapperUtils.map(foundResponse, customer);

        assertThat(response.getEmpId()).isEqualTo(foundResponse.getEmpId());
        assertThat(customer.getCustomerCode()).isEqualTo(response.getCustomerCode());
        assertNotNull(customer);
        assertNotNull(response);
        assertEquals(request.getUsername(), response.getUsername());
        assertThat(response).isInstanceOf(CustomerResponse.class);
        assertThat(customer).isInstanceOf(Customer.class);
    }

    @Test
    @DisplayName("Fetch All Customers - Service Test")
    void testFetchUsers() {

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

        userRepository.save(user);

        List<CustomerRequest> requests = new ArrayList<>();

        for (int i = 0; i <= 20; i++) {
            request = CustomerRequest.builder()
                    .username(user.getUsername())
                    .build();
            requests.add(request);
        }


        List<Customer> customers = ModelMapperUtils.mapAll(requests, Customer.class);

        List<Customer> customerList = repository.customerRepositoryEm().saveAll(customers);

        PaginateResponse<CustomerResponse> fetchUsers = customerService.fetchCustomers(0, 20);


        assertTrue((long) customerList.size() <= 20, () -> "Customer count is greater than 20");
        assertNotNull(customers);
        assertNotNull(requests);
        assertNotNull(customerList);
        assertNotNull(fetchUsers);
        assertThat(customers).isInstanceOf(List.class);
        assertThat(fetchUsers.getContent()).isInstanceOf(List.class);
        assertThat(fetchUsers.getTotalElements()).isInstanceOf(Long.class);
        assertThat(fetchUsers.getContent().size()).isGreaterThanOrEqualTo(10);
    }

}