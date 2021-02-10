package com.innova.ws.auth;

import com.innova.ws.user.User;
import com.innova.ws.user.UserService;
import com.innova.ws.user.vm.UserVM;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    UserService userService;
    PasswordEncoder passwordEncoder;

    public AuthService(UserService userService, PasswordEncoder passwordEncoder) {
        super();
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    public AuthResponse authenticate(Credentials credentials) {
        User inDB = userService.getByUsername(credentials.getUsername());
        boolean matches = passwordEncoder.matches(credentials.getPassword(), inDB.getPassword());

        if(matches) {
            UserVM userVM = new UserVM(inDB);
            String token = Jwts.builder().setSubject("" + inDB.getId()).signWith(SignatureAlgorithm.HS512, "my-app-secret").compact();
            AuthResponse response = new AuthResponse();
            response.setUser(userVM);
            response.setToken(token);
            return response;
        }

        return null;
    }
}
