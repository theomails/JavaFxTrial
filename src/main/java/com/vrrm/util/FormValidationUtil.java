package com.vrrm.util;

import lombok.Data;

public class FormValidationUtil {
    public enum ValidationMessageType { INFO, ERROR }

    @Data
    public static class ValidationMessage {
        private final ValidationMessageType type;
        private final String message;
    }

    public static ValidationMessage checkStringMinLength(String input, int minLength, String errorMsg) {
        if(input == null || input.length() < minLength) {
            return new ValidationMessage(ValidationMessageType.ERROR, errorMsg);
        }
        return null;
    }
}
