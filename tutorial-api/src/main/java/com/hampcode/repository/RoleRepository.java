package com.hampcode.repository;

import java.util.Optional;

import com.hampcode.model.ERole;
import com.hampcode.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);
}