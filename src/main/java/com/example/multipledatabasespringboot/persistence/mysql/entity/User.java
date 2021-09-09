package com.example.multipledatabasespringboot.persistence.mysql.entity;

import com.example.multipledatabasespringboot.model.enums.Status;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Project title: multiple-database-spring-boot
 *
 * @author johnadeshola
 * Date: 9/9/21
 * Time: 8:23 AM
 */
@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", length = 45, unique = true, updatable = false)
    private String username;

    @Column(name = "first_name", length = 45)
    private String firstName;

    @Column(name = "last_name", length = 45)
    private String lastName;

    @Column(name = "email", length = 120, unique = true)
    @Email
    private String email;

    @Column(name = "emp_id", length = 45)
    private String empId;

    @Column(name = "status", length = 1)
    @Builder.Default
    private Character status = Status.ACTIVE.getStatus();

    @Column(name = "date_created")
    private Timestamp dateCreated;

    @Column(name = "date_updated")
    private Timestamp dateUpdated;

    @PrePersist
    protected void onCreate() {
        this.dateCreated = new Timestamp(System.currentTimeMillis());
    }

    @PreUpdate
    protected void onUpdate() {
        this.dateUpdated = new Timestamp(System.currentTimeMillis());
    }
}