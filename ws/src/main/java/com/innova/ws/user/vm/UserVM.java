package com.innova.ws.user.vm;

import com.innova.ws.user.User;
import lombok.Data;

@Data
public class UserVM {

    private String username;

    private String fullName;

    public UserVM(User user) {
        this.setUsername(user.getUsername());
        this.setFullName(user.getFullName());
    }
}
