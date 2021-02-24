package com.innova.ws.configuration;

import com.innova.ws.error.NotFoundException;
import com.innova.ws.user.User;
import com.innova.ws.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service("userDetailsService")
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    //private WebApplicationContext applicationContext;
    private UserRepository userRepository;

//    public CustomUserDetailsService() {
//        super();
//    }
//
//    @PostConstruct
//    public void completeSetup() {
//        userRepository = applicationContext.getBean(UserRepository.class);
//    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByUsername(username);

        if (user == null) {
            throw new NotFoundException();
        }

        return new CustomUserDetails(user);
    }
}
