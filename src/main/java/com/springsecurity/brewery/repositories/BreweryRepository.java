
package com.springsecurity.brewery.repositories;

import com.springsecurity.brewery.domain.Brewery;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;


public interface BreweryRepository extends JpaRepository<Brewery, UUID> {
}
