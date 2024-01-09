package com.example.creatorconnectbackend.exceptions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import com.example.creatorconnectbackend.exceptions.GlobalExceptionHandler;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
/**
 * GlobalExceptionHandlerTest
 *
 * This class is intended to test the exception handling behaviors of the GlobalExceptionHandler.
 * Specifically, it tests the methods that handle validation exceptions occurring during the processing 
 * of API requests. The class checks that the appropriate HTTP status and error messages are returned
 * when such exceptions are thrown.
 *
 * Key Test Functions:
 * 
 * 1. testHandleValidationExceptions() - This function tests the scenario where validation exceptions
 *                                        are raised for two fields. It ensures that the GlobalExceptionHandler 
 *                                        correctly consolidates these error messages and returns them 
 *                                        with a BAD_REQUEST status. 
 *
 * Note: 
 * The tests here use mock objects to simulate the occurrence of validation exceptions and allow for 
 * controlled testing of the GlobalExceptionHandler's behavior.
 *
 */
public class GlobalExceptionHandlerTest {

    @InjectMocks
    private GlobalExceptionHandler globalExceptionHandler;

    @Test
    public void testHandleValidationExceptions() {
        // Prepare test data
        String fieldName1 = "field1";
        String errorMessage1 = "Error message 1";
        String fieldName2 = "field2";
        String errorMessage2 = "Error message 2";

        FieldError fieldError1 = new FieldError("objectName", fieldName1, errorMessage1);
        FieldError fieldError2 = new FieldError("objectName", fieldName2, errorMessage2);

        List<FieldError> fieldErrors = new ArrayList<>();
        fieldErrors.add(fieldError1);
        fieldErrors.add(fieldError2);

        BindingResult bindingResult = mock(BindingResult.class);
        when(bindingResult.getFieldErrors()).thenReturn(fieldErrors);

        MethodParameter methodParameter = mock(MethodParameter.class);
        MethodArgumentNotValidException exception = new MethodArgumentNotValidException(
                methodParameter, bindingResult);

        // Invoke the exception handler
        ResponseEntity<String> responseEntity = globalExceptionHandler.handleValidationExceptions(exception);

        // Verify the response status code
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());

        // Verify the response body
        String expectedErrorMessage = fieldName1 + ": " + errorMessage1 + ", " +
                fieldName2 + ": " + errorMessage2;
        assertEquals(expectedErrorMessage, responseEntity.getBody());
    }
}
