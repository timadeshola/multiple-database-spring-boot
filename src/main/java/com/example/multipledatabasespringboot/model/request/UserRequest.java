package com.example.multipledatabasespringboot.model.request;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * Project title: multiple-database-spring-boot
 *
 * @author johnadeshola
 * Date: 9/9/21
 * Time: 8:37 AM
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class UserRequest implements Serializable {

    @NotBlank(message = "Username cannot be empty")
    @Size(message = "Username character length cannot be less than 4 and more than 60", min = 4, max = 60)
    private String username;

    @NotBlank(message = "First name cannot be empty")
    @Size(message = "First name character length cannot be less than 4 and more than 45", min = 4, max = 45)
    private String firstName;

    @NotBlank(message = "First name cannot be empty")
    @Size(message = "Last name character length cannot be less than 4 and more than 45", min = 4, max = 45)
    private String lastName;

    @NotBlank(message = "Email address cannot be empty")
    @Size(message = "Email address character length cannot be less than 4 and more than 120", min = 4, max = 120)
    private String email;
}