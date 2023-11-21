package com.bezkoder.springjwt.api;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import com.bezkoder.springjwt.models.ERole;
import com.bezkoder.springjwt.models.ResponseObject;
import com.bezkoder.springjwt.models.Role;
import com.bezkoder.springjwt.models.User;
import com.bezkoder.springjwt.security.services.IRoleService;
import com.bezkoder.springjwt.security.services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/account")
public class AccountAPI {

    @Autowired
    private IUserService userService;

    @Autowired
    private IRoleService roleService;

    @GetMapping("/all")
    public Page<User> getUserByRole(@RequestParam("roleName") ERole roleName,
                                    @RequestParam(defaultValue = "1") int page) {

        Role role = roleService.findByName(ERole.ROLE_USER);

        return userService.getUserByRole(role, page);
    }

    @PostMapping("/save")
    public ResponseObject saveAccount(@RequestBody @Valid User dto, BindingResult result, Model model) {

        ResponseObject ro = new ResponseObject();

        if(userService.findByEmail(dto.getEmail()) != null) {
            result.rejectValue("email", "error.email","Email taken");
        }
        if(!dto.getConfirmPassword().equals(dto.getPassword())) {
            result.rejectValue("confirmPassword", "error.confirmPassword","Repassword not match");
        }

        if (result.hasErrors()) {
            setErrorsForResponseObject(result, ro);
        } else {
            ro.setStatus("success");
            userService.saveUserForMember(dto);
        }
        return ro;
    }

    @DeleteMapping("/delete/{id}")
    public void deleteAccount(@PathVariable long id) {
        userService.deleteById(id);
    }
    public void setErrorsForResponseObject(BindingResult result, ResponseObject object) {

        Map<String, String> errors = result.getFieldErrors().stream()
                .collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));
        object.setErrorMessages(errors);
        object.setStatus("fail");

        List<String> keys = new ArrayList<String>(errors.keySet());
        for (String key: keys) {
            System.out.println(key + ": " + errors.get(key));
        }

        errors = null;
    }
}
