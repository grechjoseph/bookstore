package com.jg.bookstore.domain.validation;

import com.jg.bookstore.domain.validation.checker.PhoneNumberValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target( { METHOD, FIELD, ANNOTATION_TYPE })
@Retention(RUNTIME)
@Constraint(validatedBy = PhoneNumberValidator.class)
@Documented
public @interface PhoneNumber {

    String message() default "Invalid Phone number format.";

    Class<String>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
