package com.innova.ws.user;

import com.innova.ws.error.ForbiddenException;
import com.innova.ws.error.NotFoundException;
import com.innova.ws.role.RoleService;
import com.innova.ws.user.vm.PasswordUpdateVM;
import com.innova.ws.user.vm.UserUpdateVM;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    UserRepository userRepository;
    PasswordEncoder passwordEncoder;
    RoleService roleService;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, RoleService roleService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleService = roleService;
    }

    public void save(User user, String roleName) {
        user.setPassword(this.passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);

        roleService.saveUserRole(user, roleName);
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

    public Page<User> getUsers(Pageable page, User user) {
        if(!user.getRole().getName().equals("admin")) {
            throw  new ForbiddenException();
        }

        return userRepository.findByRoleName("user", page);
    }

    public void deleteUser(String username) {
        User inDB = userRepository.findByUsername(username);
        userRepository.delete(inDB);
    }
}