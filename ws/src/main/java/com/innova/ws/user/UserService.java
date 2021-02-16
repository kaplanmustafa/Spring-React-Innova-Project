package com.innova.ws.user;

import com.innova.ws.error.NotFoundException;
import com.innova.ws.user.vm.PasswordUpdateVM;
import com.innova.ws.user.vm.UserUpdateVM;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    UserRepository userRepository;
    PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void save(User user) {
        user.setPassword(this.passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    public User getByUsername(String username) {
        User inDB = userRepository.findByUsername(username);

        if(inDB == null) {
            throw new NotFoundException();
        }

        return inDB;
    }

    public User updateUser(String username, UserUpdateVM updatedUser) {
        User inDB = getByUsername(username);
        inDB.setUsername(updatedUser.getUsername());
        inDB.setFullName(updatedUser.getFullName());

        return userRepository.save(inDB);
    }

    public void updatePassword(String username, PasswordUpdateVM passwordUpdateVM) {
        User inDB = getByUsername(username);
        inDB.setPassword(this.passwordEncoder.encode(passwordUpdateVM.getNewPassword()));
        userRepository.save(inDB);
    }
}