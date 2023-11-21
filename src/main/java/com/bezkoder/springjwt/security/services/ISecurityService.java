package com.bezkoder.springjwt.security.services;

public interface ISecurityService {

    String findLoggedInUsername();

    void autologin(String email, String password);

}
