package com.example.multipledatabasespringboot.core.exceptions.model;

import lombok.*;

import java.io.Serializable;

/**
 * Project title: multiple-database-spring-boot
 *
 * @author johnadeshola
 * Date: 9/9/21
 * Time: 8:56 AM
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class ValidationError implements Serializable {

    private Object rejectedValue;
    private String field;
    private String objectName;
}