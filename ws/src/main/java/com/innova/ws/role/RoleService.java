package com.innova.ws.role;

import com.innova.ws.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {

    @Autowired
    RoleRepository roleRepository;

    public void saveUserRole(User user, String roleName) {
        Role role = new Role();

        role.setName(roleName);
        role.setUser(user);

        roleRepository.save(role);
    }
}