package com.bezkoder.springjwt.security.services.impl;

import com.bezkoder.springjwt.models.ERole;
import com.bezkoder.springjwt.models.Role;
import com.bezkoder.springjwt.repository.IRoleRepository;
import com.bezkoder.springjwt.security.services.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements IRoleService{


    @Autowired
    private IRoleRepository roleRepository;

    @Override
    public Role findByName(ERole name) {
        return roleRepository.findByName(name)
                .orElseThrow(() -> new RuntimeException("Role not found with name: " + name));
    }


    @Override
    public List<Role> findAllRole() {
        return roleRepository.findAll();
    }


}
