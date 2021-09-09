package com.example.multipledatabasespringboot.service;

import com.example.multipledatabasespringboot.model.request.UserRequest;
import com.example.multipledatabasespringboot.model.response.PaginateResponse;
import com.example.multipledatabasespringboot.model.response.UserResponse;

/**
 * Project title: multiple-database-spring-boot
 *
 * @author johnadeshola
 * Date: 9/9/21
 * Time: 8:42 AM
 */
public interface UserService {

    public UserResponse createUser(UserRequest request);

    public UserResponse updateUser(UserRequest request, Long id);

    public Boolean deleteUser(Long id);

    public UserResponse fetchUser(Long id);

    public UserResponse fetchUser(String username);

    public PaginateResponse<UserResponse> fetchUsers(int start, int limit);
}