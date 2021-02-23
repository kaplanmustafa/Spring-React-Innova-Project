package com.innova.ws.auth;

import com.innova.ws.configuration.CustomUserDetailsService;
import com.innova.ws.jwt.JwtUtil;
import com.innova.ws.user.User;
import com.innova.ws.user.UserRepository;
import com.innova.ws.user.vm.UserVM;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    UserRepository userRepository;
    PasswordEncoder passwordEncoder;
    JwtUtil jwtUtil;
    AuthenticationManager authenticationManager;
    CustomUserDetailsService userDetailsService;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil, AuthenticationManager authenticationManager,
                       CustomUserDetailsService userDetailsService) {
        super();
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
    }

    public AuthResponse authenticate(Credentials credentials) {
        User inDB = userRepository.findByUsername(credentials.getUsername());

        if(inDB == null) {
            throw new AuthException();
        }

        if(!credentials.getRole().equals(inDB.getRole().getName())) {
            throw new AuthException();
        }

        boolean matches = passwordEncoder.matches(credentials.getPassword(), inDB.getPassword());

        if(!matches) {
            throw new AuthException();
        }

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(credentials.getUsername(), credentials.getPassword()));
        } catch (Exception ex) {
            throw new AuthException();
        }

        final UserDetails userDetails = userDetailsService.loadUserByUsername(credentials.getUsername());
        final String jwt = jwtUtil.generateToken(userDetails);

        UserVM userVM = new UserVM(inDB);
        AuthResponse response = new AuthResponse();
        response.setUser(userVM);
        response.setToken(jwt);
        response.setRole(inDB.getRole().getName());
        return response;
    }
}
