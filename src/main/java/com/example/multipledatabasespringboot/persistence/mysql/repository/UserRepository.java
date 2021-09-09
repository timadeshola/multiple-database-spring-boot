package com.example.multipledatabasespringboot.persistence.mysql.repository;

import com.example.multipledatabasespringboot.persistence.mysql.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Project title: multiple-database-spring-boot
 *
 * @author johnadeshola
 * Date: 9/9/21
 * Time: 8:24 AM
 */
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);
}