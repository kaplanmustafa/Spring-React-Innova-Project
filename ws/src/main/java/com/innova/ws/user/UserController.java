package com.innova.ws.user;

import com.innova.ws.shared.GenericResponse;
import com.innova.ws.user.vm.PasswordUpdateVM;
import com.innova.ws.user.vm.UserUpdateVM;
import com.innova.ws.user.vm.UserVM;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/1.0")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/users")
    public GenericResponse createUser(@Valid @RequestBody User user) {
        userService.save(user, "user");
        return new GenericResponse("User created");
    }

    @PutMapping("/users/{username}")
    UserVM updateUser(@Valid @RequestBody UserUpdateVM updatedUser, @PathVariable String username) {
        User user = userService.updateUser(username, updatedUser);
        return new UserVM(user);
    }

    @PutMapping("/users/password/{username}")
    @PreAuthorize("#username == principal.username")
    GenericResponse updatePassword(@Valid @RequestBody PasswordUpdateVM updatedPassword, @PathVariable String username) {
        userService.updatePassword(username, updatedPassword);
        return new GenericResponse("Password updated");
    }

    @GetMapping("/users")
    Page<UserVM> getUsers(Pageable page) {
        return userService.getUsers(page).map(UserVM::new);
    }

    @DeleteMapping("/users/{username}")
    @PreAuthorize("#username == principal.username")
    GenericResponse deleteUser(@PathVariable String username) {
        userService.deleteUser(username);
        return new GenericResponse("User removed");
    }
}
