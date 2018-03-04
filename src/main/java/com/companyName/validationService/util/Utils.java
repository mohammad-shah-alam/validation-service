package com.companyName.validationService.util;

import com.companyName.validationService.service.PasswordValidator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by mohammadshah.alam on 3/3/2018.
 */
public class Utils {

    private static Logger log = LoggerFactory.getLogger(PasswordValidator.class);
    private static ObjectMapper objectMapper = new ObjectMapper();

    /* This method will be primarily use for logging objects in json form*/
    public static String convertJsonObjectToString(Object object) {
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            log.error("Exception while trying to convert object to json ", e);
        }
        return null;
    }
}
