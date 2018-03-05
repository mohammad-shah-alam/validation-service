package unitTest.rules;

import com.companyName.validationService.validationRules.PasswordValidationRule;
import com.companyName.validationService.validationRules.SequenceRuleImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertTrue;

/**
 * Created by MOHAMMADSHAH.ALAM on 3/3/2018.
 */
public class SequenceRuleImplTest {

    private String errorMsg = "Must not contain immediately following same sequence of characters.";
    private PasswordValidationRule rule;

    @Before
    public void setUp() {
        rule = new SequenceRuleImpl(errorMsg);
    }

    @Test
    public void valid_sequence_then_return_true() {
        assertTrue("Valid password sequence failed", rule.validate("asdfg4ty"));
    }

    @Test
    public void valid_single_char_then_return_true() {
        assertTrue("Valid password sequence failed", rule.validate("s"));
    }

    @Test
    public void valid_blank_then_return_true() {
        assertTrue("Valid password sequence failed", rule.validate(""));
    }

    @Test(expected = IllegalArgumentException.class)
    public void valid_blank_spaces_then_return_true() {
        rule.validate("     ");
    }

    @Test(expected = IllegalArgumentException.class)
    public void invalid_sequence_then_return_exception() {
        rule.validate("123123");
    }

    @Test(expected = IllegalArgumentException.class)
    public void invalid_alphanumeric_sequence_then_return_exception() {
        rule.validate("123a123a");
    }
}