package com.springsecurity.brewery.repositories.security;

import com.springsecurity.brewery.domain.security.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorityRepository extends JpaRepository<Authority, Integer> {
}
