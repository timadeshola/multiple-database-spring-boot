package com.example.multipledatabasespringboot.model.response;

import lombok.*;
import org.springframework.http.HttpStatus;

import java.io.Serializable;

/**
 * Project title: multiple-database-spring-boot
 *
 * @author johnadeshola
 * Date: 9/9/21
 * Time: 8:38 AM
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class AppResponse<T> implements Serializable {

    private T data;
    private String message;
    private HttpStatus status;
    private Object error;
}