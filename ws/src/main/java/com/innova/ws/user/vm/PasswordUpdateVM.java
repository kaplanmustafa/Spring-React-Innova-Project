package com.innova.ws.user.vm;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.innova.ws.user.validator.CurrentPasswordControl;
import lombok.Data;

@Data
public class PasswordUpdateVM {

    @NotNull
    @CurrentPasswordControl
    private String currentPassword;

    @NotNull
    @Size(min = 8, max= 255)
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).*$",
            message = "{ws.constraints.password.Pattern.message}")
    private String newPassword;
}
