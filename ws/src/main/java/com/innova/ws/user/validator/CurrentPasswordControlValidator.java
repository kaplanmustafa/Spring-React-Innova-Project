package com.innova.ws.user.validator;

import com.innova.ws.user.User;
import com.innova.ws.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CurrentPasswordControlValidator implements ConstraintValidator<CurrentPasswordControl, String> {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public boolean isValid(String currentPassword, ConstraintValidatorContext context) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();

        User inDB = userRepository.findByUsername(currentPrincipalName);

        if (currentPassword != null) {
            return passwordEncoder.matches(currentPassword, inDB.getPassword());
        }

        return false;
    }
}
