package com.bezkoder.springjwt.controllers;

import com.bezkoder.springjwt.security.services.ISecurityService;
import com.bezkoder.springjwt.models.User;
import com.bezkoder.springjwt.security.services.IUserService;
import com.bezkoder.springjwt.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.validation.BindingResult;

import javax.validation.Valid;

@Controller
public class RegisterController {

    @Autowired
    private IUserService userService;

    @Autowired
    private ISecurityService securityService;

    @Autowired
    private UserValidator userValidator;


    @GetMapping("/register")
    public String registerPage(Model model) {
        model.addAttribute("newUser", new User());
        return "client/register";
    }

    @PostMapping("/register")
    public String registerProcess(@ModelAttribute("newUser") @Valid User user, BindingResult bindingResult, Model model) {

        userValidator.validate(user, bindingResult);

        if (bindingResult.hasErrors()) {
            return "client/register";
        }

        userService.saveUserForMember(user);

        securityService.autologin(user.getEmail(), user.getConfirmPassword());

        return "redirect:/";
    }
}
