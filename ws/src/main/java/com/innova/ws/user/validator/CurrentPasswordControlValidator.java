package com.innova.ws.user.validator;

import com.innova.ws.user.User;
import com.innova.ws.user.UserRepository;
import com.innova.ws.user.vm.PasswordUpdateVM;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CurrentPasswordControlValidator implements ConstraintValidator<CurrentPasswordControl, PasswordUpdateVM>{

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public boolean isValid(PasswordUpdateVM passwordUpdateVM, ConstraintValidatorContext context) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();

        User inDB = userRepository.findByUsername(currentPrincipalName);

        return passwordEncoder.matches(passwordUpdateVM.getCurrentPassword(), inDB.getPassword());
    }
}
