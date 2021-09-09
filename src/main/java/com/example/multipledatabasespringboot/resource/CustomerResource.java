package com.example.multipledatabasespringboot.resource;

import com.example.multipledatabasespringboot.model.request.CustomerRequest;
import com.example.multipledatabasespringboot.model.response.AppResponse;
import com.example.multipledatabasespringboot.model.response.CustomerResponse;
import com.example.multipledatabasespringboot.model.response.PaginateResponse;
import com.example.multipledatabasespringboot.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.example.multipledatabasespringboot.core.AppConstant.SUCCESS;

/**
 * Project title: multiple-database-spring-boot
 *
 * @author johnadeshola
 * Date: 9/9/21
 * Time: 8:44 AM
 */
@RestController
@RequestMapping("customers")
@RequiredArgsConstructor
public class CustomerResource {

    private final CustomerService customerService;


    @PostMapping
    public ResponseEntity<AppResponse<CustomerResponse>> createUser(@RequestBody @Valid CustomerRequest request) {
        CustomerResponse response = customerService.createCustomer(request);
        return ResponseEntity.ok().body(AppResponse.<CustomerResponse>builder()
                .data(response)
                .message(SUCCESS)
                .status(HttpStatus.CREATED)
                .build());
    }

    @GetMapping("fetchById/{id:[\\d]+}")
    public ResponseEntity<AppResponse<CustomerResponse>> fetchUser(@PathVariable Long id) {
        CustomerResponse response = customerService.fetchCustomer(id);
        return ResponseEntity.ok().body(AppResponse.<CustomerResponse>builder()
                .data(response)
                .message(SUCCESS)
                .status(HttpStatus.OK)
                .build());
    }

    @GetMapping("fetchByUsername/{username}")
    public ResponseEntity<AppResponse<CustomerResponse>> fetchUser(@PathVariable String username) {
        CustomerResponse response = customerService.fetchCustomer(username);
        return ResponseEntity.ok().body(AppResponse.<CustomerResponse>builder()
                .data(response)
                .message(SUCCESS)
                .status(HttpStatus.OK)
                .build());
    }

    @GetMapping("viewAll")
    public ResponseEntity<AppResponse<PaginateResponse<CustomerResponse>>> fetchUsers(
            @RequestParam int start,
            @RequestParam int limit) {
        PaginateResponse<CustomerResponse> response = customerService.fetchCustomers(start, limit);
        return ResponseEntity.ok().body(AppResponse.<PaginateResponse<CustomerResponse>>builder()
                .data(response)
                .message(SUCCESS)
                .status(HttpStatus.OK)
                .build());
    }

}