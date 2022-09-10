package com.springsecurity.brewery.repositories.security;

import com.springsecurity.brewery.domain.security.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository  extends JpaRepository<Role, Integer> {
}
