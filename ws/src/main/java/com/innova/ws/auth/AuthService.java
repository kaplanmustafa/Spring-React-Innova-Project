package com.innova.ws.auth;

import com.innova.ws.user.UserService;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    UserService userService;

    public AuthService(UserService userService) {
        super();
        this.userService = userService;
    }

    public AuthResponse authenticate(Credentials credentials) {
        // to do
        return null;
    }
}
