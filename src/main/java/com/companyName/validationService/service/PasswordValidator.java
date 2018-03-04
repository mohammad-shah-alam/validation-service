package com.companyName.validationService.service;

import com.companyName.validationService.model.ValidatePasswordRequest;
import com.companyName.validationService.validationRules.PasswordValidationRule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.List;

/**
 * Created by MOHAMMADSHAH.ALAM on 3/3/2018.
 */

@Service
public class PasswordValidator implements Validator {

    private static Logger log = LoggerFactory.getLogger(PasswordValidator.class);
    private final List<PasswordValidationRule> validationRules;

    @Autowired
    public PasswordValidator(List<PasswordValidationRule> validationRules) {
        this.validationRules = validationRules;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return ValidatePasswordRequest.class.equals(clazz);
    }

    @Override
    public void validate(@Nullable Object entityToValidate, Errors errors) {

        log.info("Going to validate entity {}", entityToValidate);

        if (validationRules == null)
        {
            log.debug("No validation rules found");
            return;
        }

        validationRules.forEach(rule -> {
            try {
                rule.validate(((ValidatePasswordRequest) entityToValidate).getPassword());
            } catch (IllegalArgumentException e) {
                errors.reject(e.getMessage());
            }
        });

        log.info("Validation result of Entity: {}, errors: {}", entityToValidate, errors);
    }
}
