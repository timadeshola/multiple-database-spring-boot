package com.example.multipledatabasespringboot.service.impl;

import com.example.multipledatabasespringboot.config.CustomDatabaseBean;
import com.example.multipledatabasespringboot.core.ModelMapperUtils;
import com.example.multipledatabasespringboot.core.exceptions.CustomException;
import com.example.multipledatabasespringboot.model.enums.Status;
import com.example.multipledatabasespringboot.model.request.UserRequest;
import com.example.multipledatabasespringboot.model.response.PaginateResponse;
import com.example.multipledatabasespringboot.model.response.UserResponse;
import com.example.multipledatabasespringboot.persistence.mysql.entity.User;
import com.example.multipledatabasespringboot.service.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
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
public class UserServiceImpl implements UserService {

    private final CustomDatabaseBean repository;

    @Override
    public UserResponse createUser(UserRequest request) {
        repository.userRepositoryEm().findByUsername(request.getUsername()).ifPresent(user -> {
            throw new CustomException(String.format("User with username %s already exist", request.getUsername()), HttpStatus.CONFLICT);
        });

        repository.userRepositoryEm().findByEmail(request.getEmail()).ifPresent(user -> {
            throw new CustomException(String.format("User with email address %s already exist", request.getEmail()), HttpStatus.CONFLICT);
        });

        return ModelMapperUtils.map(repository.userRepositoryEm().save(User.builder()
                .username(request.getUsername())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .empId(UUID.randomUUID().toString())
                .status(Status.ACTIVE.getStatus())
                .build()), UserResponse.class);
    }

    @Override
    public UserResponse updateUser(UserRequest request, Long id) {
        User user = repository.userRepositoryEm().findById(id).<CustomException>orElseThrow(() -> {
            throw new CustomException("User info cannot be found", HttpStatus.NOT_FOUND);
        });

        if (!StringUtils.isBlank(request.getEmail())) {
            user.setEmail(request.getEmail());
        }

        if (!StringUtils.isBlank(request.getFirstName())) {
            user.setFirstName(request.getFirstName());
        }

        if (!StringUtils.isBlank(request.getLastName())) {
            user.setLastName(request.getLastName());
        }

        return ModelMapperUtils.map(repository.userRepositoryEm().save(user), UserResponse.class);
    }

    @Override
    public Boolean deleteUser(Long id) {
        User user = repository.userRepositoryEm().findById(id).orElseThrow(() -> {
            throw new CustomException("User detail cannot be found", HttpStatus.NOT_FOUND);
        });
        repository.userRepositoryEm().delete(user);
        return true;
    }

    @Override
    public UserResponse fetchUser(Long id) {
        return ModelMapperUtils.map(repository.userRepositoryEm().findById(id).<CustomException>orElseThrow(() -> {
            throw new CustomException("User identity cannot be found", HttpStatus.NOT_FOUND);
        }), UserResponse.class);
    }

    @Override
    public UserResponse fetchUser(String username) {
        return ModelMapperUtils.map(repository.userRepositoryEm().findByUsername(username).<CustomException>orElseThrow(() -> {
            throw new CustomException("User identity cannot be found", HttpStatus.NOT_FOUND);
        }), UserResponse.class);
    }

    @Override
    public PaginateResponse<UserResponse> fetchUsers(int start, int limit) {
        Page<User> users = repository.userRepositoryEm().findAll(PageRequest.of(start, limit));
        if (users.isEmpty()) {
            throw new CustomException("No user info is available", HttpStatus.NO_CONTENT);
        }
        return PaginateResponse.<UserResponse>builder()
                .content(ModelMapperUtils.mapAll(users.getContent(), UserResponse.class))
                .totalElements(users.getTotalElements())
                .build();
    }
}