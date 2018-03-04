package com.companyName.validationService.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by MOHAMMADSHAH.ALAM on 3/3/2018.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
/**
 * This class is used to map any error response we want to return to the client
 * Client will get appropriate http status code with this entity and error message
 */
public class ApiGlobalErrorResponse {
    @ApiModelProperty(required = true, example = "Error Message", notes = "Password validation fixture.response message")
    private String message;
}
