package com.innova.ws.user.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target( {ElementType.TYPE , ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CurrentPasswordControlValidator.class)
public @interface CurrentPasswordControl {
    String message() default "{ws.constraints.password.Control.message}";
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default { };
}
