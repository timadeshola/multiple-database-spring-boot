package com.example.multipledatabasespringboot.resource;

import com.example.multipledatabasespringboot.model.request.UserRequest;
import com.example.multipledatabasespringboot.model.response.AppResponse;
import com.example.multipledatabasespringboot.model.response.PaginateResponse;
import com.example.multipledatabasespringboot.model.response.UserResponse;
import com.example.multipledatabasespringboot.service.UserService;
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
@RequestMapping("users")
@RequiredArgsConstructor
public class UserResource {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<AppResponse<UserResponse>> createUser(@RequestBody @Valid UserRequest request) {
        UserResponse response = userService.createUser(request);
        return ResponseEntity.ok().body(AppResponse.<UserResponse>builder()
                .data(response)
                .message(SUCCESS)
                .status(HttpStatus.CREATED)
                .build());
    }

    @PutMapping("{id:[\\d]+}")
    public ResponseEntity<AppResponse<UserResponse>> updateUser(@RequestBody UserRequest request,
                                                                @PathVariable Long id) {
        UserResponse response = userService.updateUser(request, id);
        return ResponseEntity.ok().body(AppResponse.<UserResponse>builder()
                .data(response)
                .message(SUCCESS)
                .status(HttpStatus.OK)
                .build());
    }

    @DeleteMapping("{id:[\\d]+}")
    public ResponseEntity<AppResponse<Boolean>> deleteUser(@PathVariable Long id) {
        Boolean response = userService.deleteUser(id);
        return ResponseEntity.ok().body(AppResponse.<Boolean>builder()
                .data(response)
                .message(SUCCESS)
                .status(HttpStatus.OK)
                .build());
    }

    @GetMapping("fetchById/{id:[\\d]+}")
    public ResponseEntity<AppResponse<UserResponse>> fetchUser(@PathVariable Long id) {
        UserResponse response = userService.fetchUser(id);
        return ResponseEntity.ok().body(AppResponse.<UserResponse>builder()
                .data(response)
                .message(SUCCESS)
                .status(HttpStatus.OK)
                .build());
    }

    @GetMapping("fetchByUsername/{username}")
    public ResponseEntity<AppResponse<UserResponse>> fetchUser(@PathVariable String username) {
        UserResponse response = userService.fetchUser(username);
        return ResponseEntity.ok().body(AppResponse.<UserResponse>builder()
                .data(response)
                .message(SUCCESS)
                .status(HttpStatus.OK)
                .build());
    }

    @GetMapping("viewAll")
    public ResponseEntity<AppResponse<PaginateResponse<UserResponse>>> fetchUsers(
            @RequestParam int start,
            @RequestParam int limit) {
        PaginateResponse<UserResponse> response = userService.fetchUsers(start, limit);
        return ResponseEntity.ok().body(AppResponse.<PaginateResponse<UserResponse>>builder()
                .data(response)
                .message(SUCCESS)
                .status(HttpStatus.OK)
                .build());
    }
}