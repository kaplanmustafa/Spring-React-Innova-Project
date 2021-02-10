package com.innova.ws.auth;

import com.innova.ws.user.vm.UserVM;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    @PostMapping("/api/1.0/auth")
    UserVM handleAuthentication(@RequestBody Credentials credentials) {
        // to do
        return null;
    }
}
