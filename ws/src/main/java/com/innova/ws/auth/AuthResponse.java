package com.innova.ws.auth;

import com.innova.ws.user.vm.UserVM;
import lombok.Data;

@Data
public class AuthResponse {

    private String token;

    private UserVM user;
}
