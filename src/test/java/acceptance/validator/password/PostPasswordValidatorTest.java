package acceptance.validator.password;

import com.companyName.validationService.ValidationServiceApplication;
import com.companyName.validationService.controller.ValidatorController;
import com.companyName.validationService.exception.GlobalExceptionHandler;
import com.companyName.validationService.model.ApiGlobalErrorResponse;
import com.companyName.validationService.service.PasswordValidator;
import com.companyName.validationService.validationRules.PasswordValidationRule;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static support.ResourceReader.readFixture;

/**
 * Created by MOHAMMADSHAH.ALAM on 3/3/2018.
 */

@SpringBootTest(classes = ValidationServiceApplication.class)
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
public class PostPasswordValidatorTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    @Qualifier(value = "passwordMinMaxLengthRule")
    private PasswordValidationRule passwordMinMaxLengthRule;

    @Autowired
    @Qualifier(value = "charCombinationRule")
    private PasswordValidationRule charCombinationRule;

    @Autowired
    @Qualifier(value = "sequenceRule")
    private PasswordValidationRule sequenceRule;

    private ObjectMapper mapper = new ObjectMapper();
    private ApiGlobalErrorResponse expectedResponse;
    private ApiGlobalErrorResponse actualResponse;
    private String responseBodyStr;

    @Before
    public void setup() {

        expectedResponse = null;
        actualResponse = null;

        List<PasswordValidationRule> validationRules = new ArrayList<>();
        validationRules.add(passwordMinMaxLengthRule);
        validationRules.add(charCombinationRule);
        validationRules.add(sequenceRule);

        PasswordValidator passwordValidator = new PasswordValidator(validationRules);

        this.mockMvc = MockMvcBuilders.standaloneSetup(new ValidatorController(passwordValidator))
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    public void validate_password_when_invalid_alphanumeric_password_then_return_http_200_with_no_body() throws Exception {
        mockMvc.perform(post("/validate/password")
                .contentType(APPLICATION_JSON)
                .content(readFixture("fixture/request/passwordValidation/valid_alphanumeric_request.json")))
                .andExpect(status().isOk());
    }

    @Test
    public void validate_password_when_valid_alphanumeric_password_with_cap_case_alphabet_then_return_http_400_with_body() throws Exception {
        expectedResponse = mapper
                .readValue(readFixture("fixture/response/passwordValidation/invalid_alphanumeric_must_contain_lower_letter_response.json"),
                        ApiGlobalErrorResponse.class);

        responseBodyStr = mockMvc.perform(
                post("/validate/password")
                        .contentType(APPLICATION_JSON)
                        .content(readFixture("fixture/request/passwordValidation/invalid_alphanumeric_with_cap_case_letter_request.json")))
                .andExpect(status().isBadRequest())
                .andReturn()
                .getResponse()
                .getContentAsString();

        actualResponse = mapper.readValue(responseBodyStr, ApiGlobalErrorResponse.class);
        assertEquals("Should not contains capital letter", expectedResponse, actualResponse);
    }

    @Test
    public void validate_password_when_invalid_min_length_password_then_return_http_400_with_body() throws Exception {

        expectedResponse = mapper
                .readValue(readFixture("fixture/response/passwordValidation/invalid_alphanumeric_min_max_length_response.json"),
                        ApiGlobalErrorResponse.class);

        responseBodyStr = mockMvc.perform(
                post("/validate/password")
                        .contentType(APPLICATION_JSON)
                        .content(readFixture("fixture/request/passwordValidation/invalid_alphanumeric_min_length_request.json")))
                .andExpect(status().isBadRequest())
                .andReturn()
                .getResponse()
                .getContentAsString();

        actualResponse = mapper.readValue(responseBodyStr, ApiGlobalErrorResponse.class);
        assertEquals("Invalid min length error is expected", expectedResponse, actualResponse);
    }

    @Test
    public void validate_password_when_invalid_max_length_exceed_password_then_return_http_400_with_body() throws Exception {

        expectedResponse = mapper
                .readValue(readFixture("fixture/response/passwordValidation/invalid_alphanumeric_min_max_length_response.json"),
                        ApiGlobalErrorResponse.class);

        responseBodyStr = mockMvc.perform(
                post("/validate/password")
                        .contentType(APPLICATION_JSON)
                        .content(readFixture("fixture/request/passwordValidation/invalid_alphanumeric_max_length_exceed_request.json")))
                .andExpect(status().isBadRequest())
                .andReturn()
                .getResponse()
                .getContentAsString();

        actualResponse = mapper.readValue(responseBodyStr, ApiGlobalErrorResponse.class);
        assertEquals("Invalid max length error is expected", expectedResponse, actualResponse);
    }

    @Test
    public void validate_password_when_invalid_password_with_repeat_sequence_then_return_http_400_with_body() throws Exception {

        expectedResponse = mapper
                .readValue(readFixture("fixture/response/passwordValidation/invalid_alphanumeric_with_sequence_response.json"),
                        ApiGlobalErrorResponse.class);

        responseBodyStr = mockMvc.perform(
                post("/validate/password")
                        .contentType(APPLICATION_JSON)
                        .content(readFixture("fixture/request/passwordValidation/invalid_alphanumeric_with_sequence_request.json")))
                .andExpect(status().isBadRequest())
                .andReturn()
                .getResponse()
                .getContentAsString();

        actualResponse = mapper.readValue(responseBodyStr, ApiGlobalErrorResponse.class);
        assertEquals("Invalid sequence repeat error is expected", expectedResponse, actualResponse);
    }

    @Test
    public void validate_password_when_invalid_password_with_special_char_and_repeat_sequence_then_return_http_400_with_body() throws Exception {

        expectedResponse = mapper
                .readValue(readFixture("fixture/response/passwordValidation/invalid_with_sequence_special_char_response.json"),
                        ApiGlobalErrorResponse.class);

        responseBodyStr = mockMvc.perform(
                post("/validate/password")
                        .contentType(APPLICATION_JSON)
                        .content(readFixture("fixture/request/passwordValidation/invalid_special_char_repeat_request.json")))
                .andExpect(status().isBadRequest())
                .andReturn()
                .getResponse()
                .getContentAsString();

        actualResponse = mapper.readValue(responseBodyStr, ApiGlobalErrorResponse.class);
        assertEquals("Error of Alphanumeric and repeat sequence is expected", expectedResponse, actualResponse);
    }

    @Test
    public void validate_password_when_invalid_password_with_blank_max_length_exceed_then_return_http_400_with_body() throws Exception {

        expectedResponse = mapper
                .readValue(readFixture("fixture/response/passwordValidation/invalid_blank_max_length_exceed_response.json"),
                        ApiGlobalErrorResponse.class);

        responseBodyStr = mockMvc.perform(
                post("/validate/password")
                        .contentType(APPLICATION_JSON)
                        .content(readFixture("fixture/request/passwordValidation/invalid_blank_max_length_exceed_request.json")))
                .andExpect(status().isBadRequest())
                .andReturn()
                .getResponse()
                .getContentAsString();

        actualResponse = mapper.readValue(responseBodyStr, ApiGlobalErrorResponse.class);
        assertEquals("Required Min/Max , non repeating sequence and required alphanumeric error is expected", expectedResponse, actualResponse);
    }
}
