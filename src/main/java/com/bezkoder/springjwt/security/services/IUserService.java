package com.bezkoder.springjwt.security.services;

import com.bezkoder.springjwt.models.User;
import com.bezkoder.springjwt.models.Role;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IUserService {

    User findByEmail(String email);

    User findByConfirmationToken(String confirmationToken);

    User saveUserForMember(User nd);

    User findById(long id);

    User updateUser(User nd);

    void changePass(User nd, String newPass);

    Page<User> getUserByRole(Role role, int page);

    List<User> getUserByRole(Role role);

//    User saveUserForAdmin(User dto);

    void deleteById(long id);

}
