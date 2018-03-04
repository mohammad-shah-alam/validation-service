package com.companyName.validationService.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * Created by MOHAMMADSHAH.ALAM on 3/3/2018.
 */

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ValidatePasswordRequest {

    @ApiModelProperty(required = true, example = "mySceretValue", notes = "Password value to validator")
    private String password;

   /* Override toString() method to secure the password in logging*/
    @Override
    public String toString() {
        return "ValidatePasswordRequest{" +
                "password= **** }";
    }
}
