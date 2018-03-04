package com.companyName.validationService.exception;

import com.companyName.validationService.model.ApiGlobalErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

/**
 * Created by MOHAMMADSHAH.ALAM on 3/3/2018.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiGlobalErrorResponse> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {

        String errors = ex.getBindingResult().getAllErrors().stream()
                .map(fieldError -> fieldError.getCode())
                .collect(Collectors.joining("\n"));
        return new ResponseEntity(ApiGlobalErrorResponse.builder()
                .message(errors)
                .build(), BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiGlobalErrorResponse> handleAnyUnCaughtExceptionFallOut(Exception ex) {

        return new ResponseEntity(ApiGlobalErrorResponse.builder()
                .message(ex.getMessage())
                .build(), INTERNAL_SERVER_ERROR);
    }
}