package com.bezkoder.springjwt.repository;

import java.util.Optional;

import com.bezkoder.springjwt.models.ERole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bezkoder.springjwt.models.Role;

@Repository
public interface IRoleRepository extends JpaRepository<Role, Long> {
  Optional<Role> findByName(ERole name);

//  Optional<Role> findByName(String name);
}
