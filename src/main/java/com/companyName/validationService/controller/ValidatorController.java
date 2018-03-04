package com.companyName.validationService.controller;

import com.companyName.validationService.model.ApiGlobalErrorResponse;
import com.companyName.validationService.model.ValidatePasswordRequest;
import com.companyName.validationService.service.PasswordValidator;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * Created by MOHAMMADSHAH.ALAM on 3/3/2018.
 */

@RestController
@Api(value = "/validate", description = "Validation Service")
@Slf4j
@RequestMapping(value = "/validate")
/* Base resource /validate,
 * currently this resource have one active sub resource /validate/password ,
 * if we need to add other operation then that would be /validation/theNewResourceToValidate
 * */
public class ValidatorController {

    private final PasswordValidator passwordValidator;

    @Autowired
    public ValidatorController(PasswordValidator passwordValidator) {
        this.passwordValidator = passwordValidator;
    }

    @InitBinder
    public void setupBinder(WebDataBinder binder) {
        binder.addValidators(passwordValidator);
    }

    /* Swagger annotations for api operation details*/
    @ApiOperation(value = "Validate password.",
            notes = "Validate password",
            response = ApiGlobalErrorResponse.class,
            tags = {"Validation Service",})
    /* Swagger annotation for service request and response details*/
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Valid"),
            @ApiResponse(code = 400, message = "Bad Request", response = ApiGlobalErrorResponse.class),
            @ApiResponse(code = 500, message = "Internal Server Error.", response = ApiGlobalErrorResponse.class),
    })
    /* Taking request as POST as this service do the password validation (secure information) */
    @PostMapping(value = "/password",
            produces = APPLICATION_JSON_VALUE,
            consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiGlobalErrorResponse> validatePassword(
            @ApiParam(value = "Validate password request", name = "Validate password request")
            @Valid @RequestBody ValidatePasswordRequest validatePasswordRequest
    ) {

        /**
         * Here we received a valid request ,
         * validation is done by Validator triggered by @Valid
         * (save servlet thread & save resource)
         * Purpose is to use the servlet for actual processing only and use light weight filter for the validation.
         * if valid and we have to do further logic then this request can be further passed  to service layer
         */

        ResponseEntity responseEntity = new ResponseEntity(OK);

        log.info("Finished /validator/password request = {} with responseEntity JSON = {}",
                validatePasswordRequest,
                responseEntity);

        return responseEntity;
    }
}