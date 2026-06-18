package com.quan.banking.auth.repository;

import com.quan.banking.auth.entity.Users;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Users, Long> {
    boolean existsByUsername (String username);
    boolean existsByEmail (String email);

    @EntityGraph(attributePaths = "roles")
    Optional<Users> findByUsername(String username);
}
