package com.example.electroplan_backend.validations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;

public class AtLeastOneFieldNotEmptyValidator implements ConstraintValidator<AtLeastOneFieldNotEmpty, Object> {

    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext context) {
        if (obj == null) {
            return false;
        }

        try {
            for (Field field : obj.getClass().getDeclaredFields()) {
                field.setAccessible(true);
                Object value = field.get(obj);

                if (value instanceof String && StringUtils.isNotBlank((String) value)) {
                    return true;
                } else if (value != null) {
                    return true;
                }
            }
        } catch (IllegalAccessException e) {
            // Log the error if necessary
            return false;
        }
        return false;
    }
}