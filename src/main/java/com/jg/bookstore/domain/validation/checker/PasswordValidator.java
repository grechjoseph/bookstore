package com.jg.bookstore.domain.validation.checker;

import com.jg.bookstore.domain.validation.Password;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordValidator implements ConstraintValidator<Password, String> {

    @Override
    public boolean isValid(final String value, final ConstraintValidatorContext context) {
        return value != null &&
                value.length() >= 8 && value.length() <= 20 && // has length
                !value.equals(value.toLowerCase()) && // has uppercase
                !value.equals(value.toUpperCase()) && // has lowercase
                !value.matches("[A-Za-z0-9 ]*"); // has symbols
    }

}
