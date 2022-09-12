package com.springsecurity.brewery.repositories.security;

import com.springsecurity.brewery.domain.security.LoginFailure;
import com.springsecurity.brewery.domain.security.LoginSuccess;
import com.springsecurity.brewery.domain.security.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Timestamp;
import java.util.List;

public interface LoginSuccessRepository extends JpaRepository<LoginSuccess, Integer> {
    List<LoginFailure> findAllByUserAndCreateDateAfter(User user, Timestamp timestamp);
}
