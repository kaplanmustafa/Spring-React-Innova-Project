package com.innova.ws.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueUsernameValidator implements ConstraintValidator<UniqueUsername, String>{

    @Autowired
    UserRepository userRepository;

    public boolean isValid(String username, ConstraintValidatorContext context) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();

        if(username.equals(currentPrincipalName)) {
            return true;
        }

        User user = userRepository.findByUsername(username);

        return user == null;
    }
}
