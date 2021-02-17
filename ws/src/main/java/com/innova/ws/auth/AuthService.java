package com.innova.ws.auth;

import com.innova.ws.user.User;
import com.innova.ws.user.UserRepository;
import com.innova.ws.user.vm.UserVM;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.hibernate.proxy.HibernateProxy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class AuthService {

    UserRepository userRepository;
    PasswordEncoder passwordEncoder;

    String key = "my-app-secret";

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        super();
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
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

        UserVM userVM = new UserVM(inDB);
        String token = Jwts.builder().setSubject("" + inDB.getId()).signWith(SignatureAlgorithm.HS512, key).compact();
        AuthResponse response = new AuthResponse();
        response.setUser(userVM);
        response.setToken(token);
        response.setRole(inDB.getRole().getName());
        return response;
    }

    @Transactional
    public UserDetails getUserDetails(String token) {
        JwtParser parser = Jwts.parser().setSigningKey(key);

        try {
            parser.parse(token);
            Claims claims = parser.parseClaimsJws(token).getBody();
            long userId = Long.parseLong(claims.getSubject());
            User user = userRepository.getOne(userId);
            return (User) ((HibernateProxy) user).getHibernateLazyInitializer().getImplementation();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
