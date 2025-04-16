package com.example.electroplan_backend.validations;


import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = AtLeastOneFieldNotEmptyValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface AtLeastOneFieldNotEmpty {
    String message() default "At least one field must not be empty or null";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}