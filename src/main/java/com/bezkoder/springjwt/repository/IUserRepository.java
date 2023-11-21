package com.bezkoder.springjwt.repository;

import java.util.List;
import java.util.Optional;

import com.bezkoder.springjwt.models.User;
import com.bezkoder.springjwt.models.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserRepository extends JpaRepository<User, Long> {
  User findByEmail(String email);

//  Page<User> findByRole(Set<Role> roles, Pageable of);
  Page<User> findByRoles(Role role, Pageable pageable);

//  List<User> findByRole(Set<Role> roles);
//  List<User> findByRole(Role role);
public abstract List<User> findByRoles(Role role);

  Optional<User> findByUsername(String username);

  Boolean existsByUsername(String username);

  Boolean existsByEmail(String email);
}
