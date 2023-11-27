package com.villvay.sparkprocessor.validator;

public class ValidationFunctions {

    public static boolean notNull(String value) {
        return value != null && !value.isEmpty();
    }

    public static boolean notZero(String value) {
        try {
            double numericValue = Double.parseDouble(value);
            return numericValue != 0;
        } catch (NumberFormatException e) {
            return false; // Handle non-numeric values
        }
    }

}
