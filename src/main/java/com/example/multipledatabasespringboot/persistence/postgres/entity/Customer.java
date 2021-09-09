package com.example.multipledatabasespringboot.persistence.postgres.entity;

import com.example.multipledatabasespringboot.model.enums.Status;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Project title: multiple-database-spring-boot
 *
 * @author johnadeshola
 * Date: 9/9/21
 * Time: 8:24 AM
 */
@Entity
@Table(name = "customers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Customer implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", length = 60)
    private String username;

    @Column(name = "customer_code", length = 6)
    private String customerCode;


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