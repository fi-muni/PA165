package cz.muni.fi.pa165.restapi.controllers;

import cz.muni.fi.pa165.restapi.exceptions.ErrorResource;
import cz.muni.fi.pa165.restapi.exceptions.ResourceAlreadyExistingException;
import cz.muni.fi.pa165.restapi.exceptions.ResourceNotFoundException;
import cz.muni.fi.pa165.restapi.exceptions.ServerProblemException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Converts exceptions to REST responses.
 *
 * @author Martin Kuba makub@ics.muni.cz
 */
@ControllerAdvice
public class MyExceptionHandler {

    private final static Logger log = LoggerFactory.getLogger(MyExceptionHandler.class);

    @ExceptionHandler({Exception.class})
    @ResponseBody
    protected ResponseEntity<ErrorResource> handleProblem(Exception e) {

        ErrorResource error = new ErrorResource(e.getClass().getSimpleName(), e.getMessage());

        HttpStatus httpStatus;
        if (e instanceof ServerProblemException) {
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        } else if (e instanceof ResourceNotFoundException) {
            httpStatus = HttpStatus.NOT_FOUND;
        } else if (e instanceof ResourceAlreadyExistingException) {
            httpStatus = HttpStatus.UNPROCESSABLE_ENTITY;
        } else {
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        log.debug("handleProblem({}(\"{}\")) httpStatus={}", e.getClass().getName(), e.getMessage(),httpStatus);
        return new ResponseEntity<>(error, httpStatus);
    }


}
