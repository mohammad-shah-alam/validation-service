package unitTest.rules;

import com.companyName.validationService.validationRules.PasswordValidationRule;
import com.companyName.validationService.validationRules.RegexRuleImpl;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Created by mohammadshah.alam on 3/3/2018.
 */
@RunWith(MockitoJUnitRunner.class)
public class RegexRuleImplTest {
    private String errorMsgLength = "Length must be between 5 and 12 characters";
    private PasswordValidationRule rule;

    @Test
    public void validate_with_valid_length_then_return_true() {

        rule = new RegexRuleImpl("^.{5,12}$", errorMsgLength);

        assertTrue(errorMsgLength, rule.validate("qwe123"));
        assertTrue(errorMsgLength, rule.validate("123432123456"));
        assertTrue(errorMsgLength, rule.validate("asdfghjkl"));
        assertTrue(errorMsgLength, rule.validate("12345"));
    }

    @Test
    public void validate_with_valid_alphanumeric_str_then_return_true() {

        String errorMsg = "Must consist of a mixture of lowercase letters and numerical digits only, with at least one of each.";
        rule = new RegexRuleImpl("^(?:[0-9]+[a-z]|[a-z]+[0-9])[a-z0-9]*$", errorMsg);

        assertTrue(errorMsg, rule.validate("qwesfasfasdfasdfasdfdasf123"));
        assertTrue(errorMsg, rule.validate("a1"));
        assertTrue(errorMsg, rule.validate("1qw"));
        assertTrue(errorMsg, rule.validate("1234dfasdafsfasdfasdfas5"));
    }

    @Test
    public void validate_with_invalid_min_length_then_return_error() {

        rule = new RegexRuleImpl("^.{5,12}$", errorMsgLength);

        try {
            rule.validate("12");
            fail("Should fail because of min length");
        } catch (IllegalArgumentException e) {
            assertEquals("Should fail because of min length", errorMsgLength, e.getMessage());
        }
    }

    @Test
    public void validate_with_invalid_max_length_then_return_error() {

        rule = new RegexRuleImpl("^.{5,12}$", errorMsgLength);

        try {
            rule.validate("12asdfasfasfasdfasdfasfasdfdafafsd");
            fail("Should fail because of max length");
        } catch (IllegalArgumentException e) {
            assertEquals("Should fail because of max length", errorMsgLength, e.getMessage());
        }
    }
}
