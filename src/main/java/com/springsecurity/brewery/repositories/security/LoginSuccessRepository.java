package com.springsecurity.brewery.repositories.security;

import com.springsecurity.brewery.domain.security.LoginSuccess;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoginSuccessRepository extends JpaRepository<LoginSuccess, Integer> {
}
