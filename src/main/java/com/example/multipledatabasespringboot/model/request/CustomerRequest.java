package com.example.multipledatabasespringboot.model.request;

import lombok.*;

import java.io.Serializable;

/**
 * Project title: multiple-database-spring-boot
 *
 * @author johnadeshola
 * Date: 9/9/21
 * Time: 8:49 AM
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class CustomerRequest implements Serializable {

    private String username;
}