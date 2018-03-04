package com.companyName.validationService.validationRules;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by MOHAMMADSHAH.ALAM on 3/3/2018.
 */

public class SequenceRuleImpl implements PasswordValidationRule {

    private final String errorMsg;
    private static Logger log = LoggerFactory.getLogger(SequenceRuleImpl.class);

    public SequenceRuleImpl(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    @Override
    public boolean validate(String inStr) throws IllegalArgumentException {

        if (inStr == null) {
            log.info("Input str cannot be null , returning error:{}", errorMsg);
            throw new IllegalArgumentException(errorMsg);
        }

        StringBuilder initialSequence = new StringBuilder();
        StringBuilder nextSequence = new StringBuilder();

        int indexPointer = 0;
        int offset = 1;
        int len = inStr.length();

        while (offset <= len / 2) {
            while (indexPointer + offset * 2 <= len) {
                initialSequence.append(inStr.substring(indexPointer, indexPointer + offset));
                nextSequence.append(inStr.substring(indexPointer + offset, indexPointer + offset * 2));

                indexPointer++;

                if (initialSequence.toString().equals(nextSequence.toString())) {
                    log.info("Sequence validation failed, returning error:{}", errorMsg);
                    throw new IllegalArgumentException(errorMsg);
                }

                initialSequence.delete(0, initialSequence.length());
                nextSequence.delete(0, nextSequence.length());
            }
            indexPointer = 0;
            offset++;
        }
        return true;
    }
}
   
