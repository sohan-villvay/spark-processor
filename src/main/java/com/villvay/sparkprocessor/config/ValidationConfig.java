package com.villvay.sparkprocessor.config;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class ValidationConfig {

    @JsonProperty("file")
    private String file;
    @JsonProperty("delimiter")
    private String delimiter;
    @JsonProperty("format")
    private String format;
    @JsonProperty("validations")
    private List<FieldValidationConfig> validations;


    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public String getDelimiter() {
        return delimiter;
    }

    public void setDelimiter(String delimiter) {
        this.delimiter = delimiter;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public List<FieldValidationConfig> getValidations() {
        return validations;
    }

    public void setValidations(List<FieldValidationConfig> validations) {
        this.validations = validations;
    }
}
