package com.example.multipledatabasespringboot.core.exceptions.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

/**
 * Project title: multiple-database-spring-boot
 *
 * @author johnadeshola
 * Date: 9/9/21
 * Time: 8:55 AM
 */
@Setter
@Getter
@NoArgsConstructor
@Builder
public class ErrorDetails implements Serializable {

    private LocalDate timestamp;
    private String message;
    private String details;
    private int code;
    private List<ValidationError> validation;

    public ErrorDetails(LocalDate timestamp, String message, String details, int code) {
        this.timestamp = timestamp;
        this.message = message;
        this.details = details;
        this.code = code;
    }

    public ErrorDetails(LocalDate timestamp, String message, String details, int code, List<ValidationError> validation) {
        this.timestamp = timestamp;
        this.message = message;
        this.details = details;
        this.code = code;
        this.validation = validation;
    }
}