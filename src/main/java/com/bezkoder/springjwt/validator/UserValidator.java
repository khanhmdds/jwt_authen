package com.bezkoder.springjwt.validator;

import com.bezkoder.springjwt.models.User;
import com.bezkoder.springjwt.security.services.IUserService;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class UserValidator implements Validator {

    @Autowired
    private IUserService userService;

    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        User user = (User) target;

        ValidationUtils.rejectIfEmpty(errors, "full_name", "error.full_name", "Not empty");
        ValidationUtils.rejectIfEmpty(errors, "phone", "error.phone", "Not empty");
        ValidationUtils.rejectIfEmpty(errors, "address", "error.address", "Not empty");

        // validate cho email
        // check ko đc trống
        ValidationUtils.rejectIfEmpty(errors, "email", "error.email", "Not empty");

        // check địa chỉ email phù hợp hay không
        Pattern pattern = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

        // check sdt
        final String PHONE = "^0[1-9][0-9]{8,9}";
        Pattern sdt = Pattern.compile(PHONE, Pattern.CASE_INSENSITIVE);

        if (!(sdt.matcher(user.getPhone()).matches())) {
            errors.rejectValue("phone", "error.phone", "Invalid");
        }
        if (!(pattern.matcher(user.getEmail()).matches())) {
            errors.rejectValue("email", "error.email", "Invalid");
        }

        // check địa chi email đã được dùng chưa
        if (userService.findByEmail(user.getEmail()) != null) {
            errors.rejectValue("email", "error.email", "Email taken");
        }

        // check password trống hay không
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "error.password", "Not empty");

        // check confirmPassword trống hay không
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "confirmPassword", "error.confirmPassword",
                "Not empty");

        // check độ dài password (8-32)
        if (user.getPassword().length() < 8 || user.getPassword().length() > 32) {
            errors.rejectValue("password", "error.password", "8-32 length");
        }

        // check match pass và confirmPass
        if (!user.getConfirmPassword().equals(user.getPassword())) {
            errors.rejectValue("confirmPassword", "error.confirmPassword", "Re-password not match");
        }
    }

}
