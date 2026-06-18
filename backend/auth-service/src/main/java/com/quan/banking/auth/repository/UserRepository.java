package com.quan.banking.auth.repository;

import com.quan.banking.auth.entity.Users;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Users, Long> {
    boolean existsByUsernameAndDeletedFalse(String username);

    boolean existsByEmailAndDeletedFalse(String email);

    @EntityGraph(attributePaths = {"roles", "roles.permissions"})
    Optional<Users> findByUsernameAndDeletedFalse(String username);


}
