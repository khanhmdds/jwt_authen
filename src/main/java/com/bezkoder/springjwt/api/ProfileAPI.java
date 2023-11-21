package com.bezkoder.springjwt.api;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.bezkoder.springjwt.models.ResponseObject;
import com.bezkoder.springjwt.models.User;
import com.bezkoder.springjwt.models.dto.PasswordDTO;
import com.bezkoder.springjwt.security.services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequestMapping("/api/profile")
public class ProfileAPI {

    @Autowired
    private IUserService userService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @GetMapping("/{id}")
    public User getUserById(@PathVariable long id) {
        User nd = userService.findById(id);
        return nd;
    }

    @PostMapping("/changePassword")
    public ResponseObject changePass(@RequestBody @Valid PasswordDTO dto, BindingResult result,
                                     HttpServletRequest request) {
        System.out.println(dto.toString());
        User currentUser = getSessionUser(request);

        ResponseObject ro = new ResponseObject();

        if (!passwordEncoder.matches( dto.getOldPassword(), currentUser.getPassword())) {
            result.rejectValue("oldPassword", "error.oldPassword", "Password not match");
        }

        if (!dto.getNewPassword().equals(dto.getConfirmNewPassword())) {
            result.rejectValue("confirmNewPassword", "error.confirmNewPassword", "Repassword not match");
        }

        if (result.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            List<FieldError> errorsList = result.getFieldErrors();
            for (FieldError error : errorsList ) {
                errors.put(error.getField(), error.getDefaultMessage());
            }
            ro.setErrorMessages(errors);
            ro.setStatus("fail");
            errors = null;
        } else {
            userService.changePass(currentUser, dto.getNewPassword());
            ro.setStatus("success");
        }

        return ro;
    }

    public User getSessionUser(HttpServletRequest request) {
        return (User) request.getSession().getAttribute("loggedInUser");
    }
}
