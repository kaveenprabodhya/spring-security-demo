package com.springsecurity.brewery.repositories.security;

import com.springsecurity.brewery.domain.security.LoginFailure;
import com.springsecurity.brewery.domain.security.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Timestamp;
import java.util.List;

public interface LoginFailureRepository extends JpaRepository<LoginFailure, Integer> {
    List<LoginFailure> findAllByUserAndCreatedDateIsAfter(User user, Timestamp timestamp);
}
