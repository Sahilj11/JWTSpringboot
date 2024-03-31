package com.sec.sec.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sec.sec.model.AppUser;

/**
 * AppUserRepo
 */
@Repository
public interface AppUserRepo extends JpaRepository<AppUser, Long> {

    boolean existsByUsername(String username);

    Optional<AppUser> findByUsername(String username);
}
