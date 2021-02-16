package com.innova.ws.user.vm;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class UserUpdateVM {

    @NotNull(message = "{ws.constraints.username.NotNull.message}")
    @Size(min = 4, max= 255)
    private String username;

    @NotNull
    @Size(min = 2, max= 255)
    private String fullName;
}
