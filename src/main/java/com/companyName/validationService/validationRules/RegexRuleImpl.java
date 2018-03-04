package com.companyName.validationService.validationRules;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by MOHAMMADSHAH.ALAM on 3/3/2018.
 */

public class RegexRuleImpl implements PasswordValidationRule {

    private final String regexRule;
    private final String errorMsg;
    private final Pattern pattern;
    private static Logger log = LoggerFactory.getLogger(RegexRuleImpl.class);


    public RegexRuleImpl(String regexRule, String errorMsg) {
        this.regexRule = regexRule;
        this.errorMsg = errorMsg;

        /*Pattern is thread safe*/
        this.pattern = Pattern.compile(this.regexRule);
    }

    @Override
    public boolean validate(String inStr) throws IllegalArgumentException {

        if (inStr == null) {
            log.info("Validation str cannot be null regex: {} ,  returning error:{}",
                    regexRule,
                    errorMsg);
            throw new IllegalArgumentException(errorMsg);
        }

        boolean isValid;

        Matcher matcher = pattern.matcher(inStr);
        isValid = matcher.matches();

        if (!isValid) {
            log.info("Validation failed for regex: {} ,returning error:{}",
                    regexRule,
                    errorMsg);
            throw new IllegalArgumentException(errorMsg);
        }
        return isValid;
    }
}
