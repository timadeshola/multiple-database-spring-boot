package com.example.multipledatabasespringboot.model.response;

import lombok.*;

import java.io.Serializable;
import java.util.List;

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
public class PaginateResponse<T> implements Serializable {

    private List<T> content;
    private long totalElements;
}