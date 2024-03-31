package com.sec.sec.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sec.sec.model.Role;

/**
 * RoleRepo
 */
@Repository
public interface RoleRepo extends JpaRepository<Role, Long> {

    Optional<Role> findByName(String name);
}
