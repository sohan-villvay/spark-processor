package com.villvay.sparkprocessor.validator;

@FunctionalInterface
public interface FieldValidation {
    boolean validate(String value);
}


