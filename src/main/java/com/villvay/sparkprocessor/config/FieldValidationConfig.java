package com.villvay.sparkprocessor.config;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class FieldValidationConfig {

    @JsonProperty("fieldName")
    private String fieldName;
    @JsonProperty("validations")
    private List<String> validations;
    

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public List<String> getValidations() {
        return validations;
    }

    public void setValidations(List<String> validations) {
        this.validations = validations;
    }
}
