package com.example.multipledatabasespringboot.model.response;

import lombok.*;

import java.io.Serializable;

/**
 * Project title: multiple-database-spring-boot
 *
 * @author johnadeshola
 * Date: 9/9/21
 * Time: 8:39 AM
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class CustomerResponse implements Serializable {

    private Long id;
    private String username;
    private String customerCode;
    private String firstName;
    private String lastName;
    private String email;
    private String empId;
    private String status;
    private String dateCreated;
    private String dateUpdated;
}