package com.example.multipledatabasespringboot.core.exceptions;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

/**
 * Project title: multiple-database-spring-boot
 *
 * @author johnadeshola
 * Date: 9/9/21
 * Time: 8:53 AM
 */
@Getter
@NoArgsConstructor
public class CustomException extends RuntimeException {

    protected String message;
    protected HttpStatus status;

    public CustomException(String message) {
        super(message);
        this.message = message;
    }

    public CustomException(String message, HttpStatus status) {
        this.message = message;
        this.status = status;
    }
}