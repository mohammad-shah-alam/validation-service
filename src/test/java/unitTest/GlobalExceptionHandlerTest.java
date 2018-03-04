package unitTest;
/**
 * Created by MOHAMMADSHAH.ALAM on 3/3/2018.
 */

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

@SpringBootTest(classes = ValidationServiceApplication.class)
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
public class GlobalExceptionHandlerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    @Qualifier(value = "passwordMinMaxLengthRule")
    private PasswordValidationRule passwordMinMaxLengthRule;

    private ObjectMapper mapper = new ObjectMapper();


    @Before
    public void setup() throws NoSuchMethodException {

        List<PasswordValidationRule> validationRules = new ArrayList<>();
        validationRules.add(passwordMinMaxLengthRule);

        PasswordValidator passwordValidator = new PasswordValidator(validationRules);
        this.mockMvc = MockMvcBuilders.standaloneSetup(new ValidatorController(passwordValidator))
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    public void throw_method_argument_exception_when_validate_password() throws Exception {

        ApiGlobalErrorResponse expectedResponse = ApiGlobalErrorResponse.builder()
                .message("Must be between 5 and 12 characters in length.")
                .build();

        String responseBodyStr = mockMvc.perform(post("/validate/password")
                .contentType(APPLICATION_JSON)
                .content(readFixture("fixture/request/passwordValidation/invalid_alphanumeric_max_length_exceed_request.json")))
                .andExpect(status().isBadRequest())
                .andReturn()
                .getResponse()
                .getContentAsString();

        ApiGlobalErrorResponse actualResponse = mapper.readValue(responseBodyStr, ApiGlobalErrorResponse.class);
        assertEquals(expectedResponse, actualResponse);
    }
}
