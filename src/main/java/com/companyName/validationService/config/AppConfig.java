package com.companyName.validationService.config;

import com.companyName.validationService.validationRules.PasswordValidationRule;
import com.companyName.validationService.validationRules.RegexRuleImpl;
import com.companyName.validationService.validationRules.SequenceRuleImpl;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MOHAMMADSHAH.ALAM on 3/3/2018.
 */
@Configuration
public class AppConfig {

    @Bean
    public List<PasswordValidationRule> validationRules(
           @Qualifier(value = "passwordMinMaxLengthRule") PasswordValidationRule passwordMinMaxLengthRule,
           @Qualifier(value = "charCombinationRule") PasswordValidationRule charCombinationRule,
           @Qualifier(value = "sequenceRule") PasswordValidationRule sequenceRule
    ) {
        List<PasswordValidationRule> validationRules = new ArrayList<>();
        validationRules.add(passwordMinMaxLengthRule);
        validationRules.add(charCombinationRule);
        validationRules.add(sequenceRule);
        return validationRules;
    }

    @Bean(name = "passwordMinMaxLengthRule")
    public PasswordValidationRule passwordMinMaxLengthRule(
            @Value("${app.validation.password.length.lengthRuleRegex}") String lengthRuleRegex,
            @Value("${app.validation.password.length.errorMsg}") String errorMsg) {
        return new RegexRuleImpl(lengthRuleRegex, errorMsg);
    }

    @Bean(name = "charCombinationRule")
    public PasswordValidationRule charCombinationRule(
            @Value("${app.validation.password.charCombination.charactersRuleRegex}") String charactersRuleRegex,
            @Value("${app.validation.password.charCombination.errorMsg}") String errorMsg) {
        return new RegexRuleImpl(charactersRuleRegex, errorMsg);
    }

    @Bean(name = "sequenceRule")
    public PasswordValidationRule sequenceRule(
            @Value("${app.validation.password.sequenceRule.errorMsg}") String errorMsg) {
        return new SequenceRuleImpl(errorMsg);
    }
}
