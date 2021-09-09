package com.example.multipledatabasespringboot.service.impl;

import com.example.multipledatabasespringboot.config.CustomDatabaseBean;
import com.example.multipledatabasespringboot.core.ModelMapperUtils;
import com.example.multipledatabasespringboot.core.exceptions.CustomException;
import com.example.multipledatabasespringboot.model.request.CustomerRequest;
import com.example.multipledatabasespringboot.model.response.CustomerResponse;
import com.example.multipledatabasespringboot.model.response.PaginateResponse;
import com.example.multipledatabasespringboot.persistence.mysql.entity.User;
import com.example.multipledatabasespringboot.persistence.postgres.entity.Customer;
import com.example.multipledatabasespringboot.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Project title: multiple-database-spring-boot
 *
 * @author johnadeshola
 * Date: 9/9/21
 * Time: 8:42 AM
 */
@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomDatabaseBean repository;

    @Override
    public CustomerResponse createCustomer(CustomerRequest request) {
        repository.customerRepositoryEm().findByUsername(request.getUsername()).ifPresent(customer -> {
            throw new CustomException("Customer already exist", HttpStatus.CONFLICT);
        });

        repository.userRepositoryEm().findByUsername(request.getUsername()).<CustomException>orElseThrow(() -> {
            throw new CustomException(String.format("User with username %s does not exist", request.getUsername()), HttpStatus.NOT_FOUND);
        });

        return ModelMapperUtils.map(repository.customerRepositoryEm().save(Customer.builder()
                .customerCode(UUID.randomUUID().toString())
                .username(request.getUsername())
                .build()), CustomerResponse.class);
    }

    @Override
    public CustomerResponse fetchCustomer(Long id) {
        Customer customer = repository.customerRepositoryEm().findById(id).<CustomException>orElseThrow(() -> {
            throw new CustomException("Customer info cannot be found", HttpStatus.NOT_FOUND);
        });

        User user = repository.userRepositoryEm().findByUsername(customer.getUsername()).<CustomException>orElseThrow(() -> {
            throw new CustomException("User with customerId cannot be found", HttpStatus.NOT_FOUND);
        });
        return CustomerResponse.builder()
                .id(customer.getId())
                .customerCode(customer.getCustomerCode())
                .username(customer.getUsername())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .empId(user.getEmpId())
                .dateCreated(String.valueOf(customer.getDateCreated()))
                .dateUpdated(String.valueOf(customer.getDateUpdated()))
                .build();
    }

    @Override
    public CustomerResponse fetchCustomer(String username) {
        Customer customer = repository.customerRepositoryEm().findByUsername(username).<CustomException>orElseThrow(() -> {
            throw new CustomException("Customer info cannot be found", HttpStatus.NOT_FOUND);
        });

        User user = repository.userRepositoryEm().findByUsername(customer.getUsername()).<CustomException>orElseThrow(() -> {
            throw new CustomException("User with customerId cannot be found", HttpStatus.NOT_FOUND);
        });
        return CustomerResponse.builder()
                .id(customer.getId())
                .customerCode(customer.getCustomerCode())
                .username(customer.getUsername())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .empId(user.getEmpId())
                .dateCreated(String.valueOf(customer.getDateCreated()))
                .dateUpdated(String.valueOf(customer.getDateUpdated()))
                .build();
    }

    @Override
    public PaginateResponse<CustomerResponse> fetchCustomers(int start, int limit) {
        Page<Customer> users = repository.customerRepositoryEm().findAll(PageRequest.of(start, limit));
        if (users.isEmpty()) {
            throw new CustomException("No user info is available", HttpStatus.NO_CONTENT);
        }
        return PaginateResponse.<CustomerResponse>builder()
                .content(ModelMapperUtils.mapAll(users.getContent(), CustomerResponse.class))
                .totalElements(users.getTotalElements())
                .build();
    }

}
