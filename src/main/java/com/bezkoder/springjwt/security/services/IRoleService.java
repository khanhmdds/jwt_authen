package com.bezkoder.springjwt.security.services;

import com.bezkoder.springjwt.models.ERole;
import com.bezkoder.springjwt.models.Role;

import java.util.List;

public interface IRoleService {
//    Role findByName(String name);
    Role findByName(ERole name);

    List<Role> findAllRole();
}
