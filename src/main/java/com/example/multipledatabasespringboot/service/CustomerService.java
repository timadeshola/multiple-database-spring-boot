package com.example.multipledatabasespringboot.service;

import com.example.multipledatabasespringboot.model.request.CustomerRequest;
import com.example.multipledatabasespringboot.model.response.CustomerResponse;
import com.example.multipledatabasespringboot.model.response.PaginateResponse;

/**
 * Project title: multiple-database-spring-boot
 *
 * @author johnadeshola
 * Date: 9/9/21
 * Time: 8:42 AM
 */
public interface CustomerService {

    CustomerResponse createCustomer(CustomerRequest request);

    CustomerResponse fetchCustomer(Long id);

    CustomerResponse fetchCustomer(String username);

    public PaginateResponse<CustomerResponse> fetchCustomers(int start, int limit);
}