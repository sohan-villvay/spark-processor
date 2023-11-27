package com.villvay.sparkprocessor.validator;

import com.villvay.sparkprocessor.config.FieldValidationConfig;
import com.villvay.sparkprocessor.config.ValidationConfig;
import java.util.Arrays;

import static com.villvay.sparkprocessor.validator.ValidationFunctions.notNull;
import static com.villvay.sparkprocessor.validator.ValidationFunctions.notZero;

public class Validator {
    public static String validateLine(String line, ValidationConfig config) {

        // pass json or pojo

        for (FieldValidationConfig fieldConfig : config.getValidations()) {
            int index = Arrays.asList(fields).indexOf(fieldConfig.getFieldName());
            if (index >= 0) {
                for (String validation : fieldConfig.getValidations()) {
                    if (!applyValidation(fields[index], validation)) {
                        System.out.println("Validation "+validation+ " Failed at "+ fields[index]);
                    }
                }
            }
        }

        return String.join(config.getDelimiter(), fields);
    }

    private static boolean applyValidation(String fieldValue, String validation) {
        switch (validation) {
            case "notNull":
                return notNull(fieldValue);
            case "notZero":
                return notZero(fieldValue);
            default:
                return false;
        }
    }
}
