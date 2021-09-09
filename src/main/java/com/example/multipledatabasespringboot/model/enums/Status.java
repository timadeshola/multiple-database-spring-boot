package com.example.multipledatabasespringboot.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Project title: multiple-database-spring-boot
 *
 * @author johnadeshola
 * Date: 9/9/21
 * Time: 8:28 AM
 */
@Getter
@AllArgsConstructor
public enum Status {

    ACTIVE('0'),
    INACTIVE('1');

    private Character status;
}