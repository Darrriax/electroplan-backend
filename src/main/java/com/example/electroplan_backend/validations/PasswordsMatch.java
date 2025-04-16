package com.example.electroplan_backend.validations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PasswordsMatchValidator.class)
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface PasswordsMatch {
    String message() default "Паролі повинні співпадати";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}