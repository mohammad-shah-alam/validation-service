package com.companyName.validationService.validationRules;

/**
 * Created by MOHAMMADSHAH.ALAM on 3/3/2018.
 */
public interface PasswordValidationRule {
    boolean validate(String inStr) throws IllegalArgumentException;
}
