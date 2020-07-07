package com.khanhbn.hibernate.validation;

import org.apache.logging.log4j.util.Strings;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class TestValidator implements ConstraintValidator<test, String> {

    private static final String PREFIX = "http://";

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if (Strings.isBlank(s)) {
            return false;
        }
        return s.startsWith(PREFIX);
    }
}
