package com.bezkoder.springjwt.security.services.impl;

import com.bezkoder.springjwt.models.ERole;
import com.bezkoder.springjwt.models.Role;
import com.bezkoder.springjwt.models.User;
import com.bezkoder.springjwt.repository.IRoleRepository;
import com.bezkoder.springjwt.repository.IUserRepository;
import com.bezkoder.springjwt.security.services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class UserServiceImpl implements IUserService {

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private IRoleRepository roleRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User findByConfirmationToken(String confirmationToken) {
        return null;
    }

//    @Override
//    public User saveUserForMember(User nd) {
//        nd.setPassword(bCryptPasswordEncoder.encode(nd.getPassword()));
//        Set<Role> roles = new HashSet<>();
//        roles.add(roleRepository.findByName(ERole.ROLE_USER));
//        nd.setRoles(roles);
//        return userRepository.save(nd);
//    }

    @Override
    public User saveUserForMember(User nd) {
        nd.setPassword(bCryptPasswordEncoder.encode(nd.getPassword()));

        // Tìm kiếm vai trò ROLE_USER từ roleRepository
        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                .orElseThrow(() -> new RuntimeException("Error: Role not found."));

//        Set<Role> roles = new HashSet<>();
//        roles.add(userRole);
//
//        nd.setRoles(roles);
        Set<Role> role = new HashSet<>();
        role.add(userRole);
        nd.setRoles(role);
        return userRepository.save(nd);
    }


    @Override
    public User findById(long id) {
        User nd = userRepository.findById(id).get();
        return nd;
    }

    @Override
    public User updateUser(User nd) {
        return userRepository.save(nd);
    }

    @Override
    public void changePass(User nd, String newPass) {
        nd.setPassword(bCryptPasswordEncoder.encode(newPass));
        userRepository.save(nd);
    }

//    @Override
//    public Page<User> getUserByRole(Set<Role> roles, int page) {
//        return userRepository.findByRole(roles, PageRequest.of(page - 1, 6));
//    }
//
//    @Override
//    public List<User> getUserByRole(Set<Role> roles) {
//        return userRepository.findByRole(roles);
//    }

    public Page<User> getUserByRole(Role role, int page) {
        return userRepository.findByRoles(role, PageRequest.of(page - 1, 6));
    }

    public List<User> getUserByRole(Role role) {
        return userRepository.findByRoles(role);
    }

//    @Override
//    public User saveUserForAdmin(User dto) {
//        User nd = new User();
//        nd.setFull_name(dto.getFull_name());
//        nd.setAddress(dto.getAddress());
//        nd.setEmail(dto.getEmail());
//        nd.setPhone(dto.getPhone());
//        nd.setPassword(bCryptPasswordEncoder.encode(dto.getPassword()));
//
//        Set<Role> roles  = new HashSet<>();
//        roles.add(roleRepository.findByName(dto.getRoles()));
//        nd.setVaiTro(roles);
//
//        return userRepository.save(nd);
//    }

    @Override
    public void deleteById(long id) {
        userRepository.deleteById(id);
    }

}
