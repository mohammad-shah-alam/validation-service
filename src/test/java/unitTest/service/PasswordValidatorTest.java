package unitTest.service;

import com.companyName.validationService.model.ValidatePasswordRequest;
import com.companyName.validationService.service.PasswordValidator;
import com.companyName.validationService.validationRules.PasswordValidationRule;
import com.companyName.validationService.validationRules.RegexRuleImpl;
import com.companyName.validationService.validationRules.SequenceRuleImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.validation.BeanPropertyBindingResult;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class PasswordValidatorTest {

    @Mock
    private RegexRuleImpl regexRule;

    @Mock
    private SequenceRuleImpl sequenceRule;

    private PasswordValidator passwordValidator;
    private ObjectMapper mapper = new ObjectMapper();
    private BeanPropertyBindingResult bindingResult;
    private ValidatePasswordRequest validatePasswordRequest;

    @Before
    public void setup() {

        List<PasswordValidationRule> validationRules = new ArrayList<>();
        validationRules.add(regexRule);
        validationRules.add(sequenceRule);
        validatePasswordRequest = null;

        passwordValidator = new PasswordValidator(validationRules);
    }

    @Test
    public void validate_valid_input_then_no_error() {

        validatePasswordRequest = ValidatePasswordRequest.builder()
                .password("123werty")
                .build();

        bindingResult = new BeanPropertyBindingResult(validatePasswordRequest,
                validatePasswordRequest.getClass().getName());

        passwordValidator.validate(validatePasswordRequest, bindingResult);

        verify(regexRule, times(1)).validate(validatePasswordRequest.getPassword());
        verify(sequenceRule, times(1)).validate(validatePasswordRequest.getPassword());

        assertFalse(bindingResult.hasErrors());
    }

    @Test
    public void validate_valid_input_and_empty_rules_then_no_error() {

        validatePasswordRequest = ValidatePasswordRequest.builder()
                .password("123werty")
                .build();

        bindingResult = new BeanPropertyBindingResult(validatePasswordRequest,
                validatePasswordRequest.getClass().getName());

        passwordValidator = new PasswordValidator(new ArrayList<>());
        passwordValidator.validate(validatePasswordRequest, bindingResult);

        verify(regexRule, never()).validate(validatePasswordRequest.getPassword());
        verify(sequenceRule, never()).validate(validatePasswordRequest.getPassword());
        assertFalse(bindingResult.hasErrors());
    }

    @Test
    public void validate_valid_input_and_null_rules_then_no_error() {

        validatePasswordRequest = ValidatePasswordRequest.builder()
                .password("123a123a")
                .build();

        bindingResult = new BeanPropertyBindingResult(validatePasswordRequest,
                validatePasswordRequest.getClass().getName());

        passwordValidator = new PasswordValidator(null);
        passwordValidator.validate(validatePasswordRequest, bindingResult);

        verify(regexRule, never()).validate(validatePasswordRequest.getPassword());
        verify(sequenceRule, never()).validate(validatePasswordRequest.getPassword());
        assertFalse(bindingResult.hasErrors());

    }

    @Test
    public void validate_invalid_input_and_regex_validator_failed_then_error() {

        when(regexRule.validate(anyString())).thenThrow(
                new IllegalArgumentException("Max/Min regex validation failed"));
        when(sequenceRule.validate(anyString())).thenReturn(true);

        validatePasswordRequest = ValidatePasswordRequest.builder()
                .password("123wertykjhkjhkjhkjhkjhkjhkjhk")
                .build();

        bindingResult = new BeanPropertyBindingResult(validatePasswordRequest,
                validatePasswordRequest.getClass().getName());

        passwordValidator.validate(validatePasswordRequest, bindingResult);

        verify(regexRule, times(1)).validate(validatePasswordRequest.getPassword());
        verify(sequenceRule, times(1)).validate(validatePasswordRequest.getPassword());

        assertTrue(bindingResult.hasErrors());
        assertEquals("Max/Min regex validation failed error expected",
                "Max/Min regex validation failed",
                bindingResult.getAllErrors().get(0).getCode());
    }

    @Test
    public void validate_invalid_input_and_sequence_validator_failed_then_error() {

        when(regexRule.validate(anyString())).thenReturn(true);
        when(sequenceRule.validate(anyString())).thenThrow(new IllegalArgumentException("Sequence validation failed"));

        validatePasswordRequest = ValidatePasswordRequest.builder()
                .password("123werty%%")
                .build();

        bindingResult = new BeanPropertyBindingResult(validatePasswordRequest,
                validatePasswordRequest.getClass().getName());

        passwordValidator.validate(validatePasswordRequest, bindingResult);

        verify(regexRule, times(1)).validate(validatePasswordRequest.getPassword());
        verify(sequenceRule, times(1)).validate(validatePasswordRequest.getPassword());

        assertTrue(bindingResult.hasErrors());
        assertEquals("Sequence validation failed error expected",
                "Sequence validation failed",
                bindingResult.getAllErrors().get(0).getCode());
    }
}
