package com.jg.bookstore.domain.validation.checker;

import com.jg.bookstore.domain.validation.PhoneNumber;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PhoneNumberValidator implements ConstraintValidator<PhoneNumber, String> {

    @Override
    public boolean isValid(final String value, final ConstraintValidatorContext context) {
        return value.matches("^\\+?\\d*$"); // has symbols
    }

}
