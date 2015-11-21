package cz.fi.muni.pa165.rest;

import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Represents a possible representation of errors to be used 
 * with @ControllerAdvice global exception handler
 * 
 * @author brossi
 */
//@XmlRootElement
public class ApiError {
    
    private List<String> errors;

    public ApiError() {
    }

    public ApiError(List<String> errors) {
        this.errors = errors;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }
}
