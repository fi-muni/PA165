package cz.fi.muni.pa165.rest.controllers;

import cz.fi.muni.pa165.rest.ApiError;
import cz.fi.muni.pa165.rest.exceptions.ResourceAlreadyExistingException;
import java.util.Arrays;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * This shows how a @ControllerAdvice can be used so that all the 
 * handlers can be managed from a central location.
 * It is also possible to associate one handler to a set of Controllers
 * See https://docs.spring.io/spring/docs/current/javadoc-api/org/springframework/web/bind/annotation/ControllerAdvice.html
 * 
 * @author brossi
 */
@ControllerAdvice
public class GlobalExceptionController {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ResponseBody
    ApiError handleException(ResourceAlreadyExistingException ex) {
        ApiError apiError = new ApiError();
        apiError.setErrors(Arrays.asList("the requested resource already exists"));
        return apiError;
    }
}