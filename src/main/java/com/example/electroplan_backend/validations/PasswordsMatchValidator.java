package com.example.electroplan_backend.validations;

import com.example.electroplan_backend.dto.requests.UserRegistrationRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordsMatchValidator implements ConstraintValidator<PasswordsMatch, UserRegistrationRequest> {

    @Override
    public boolean isValid(UserRegistrationRequest request, ConstraintValidatorContext context) {
        if (request.getPassword() == null || request.getPasswordConfirmation() == null) {
            return false;
        }

        boolean isValid = request.getPassword().equals(request.getPasswordConfirmation());

        if (!isValid) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Паролі повинні співпадати")
                    .addPropertyNode("passwordConfirmation")
                    .addConstraintViolation();
        }

        return isValid;
    }
}