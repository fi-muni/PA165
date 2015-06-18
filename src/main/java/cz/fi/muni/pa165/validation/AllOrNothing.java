package cz.fi.muni.pa165.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

import javax.validation.Constraint;
import javax.validation.Payload;


@Target( { TYPE })
@Retention(RUNTIME)
@Constraint(validatedBy = AllOrNothingValidator.class)
@Documented
public @interface AllOrNothing {

    String message() default "All of the following fields must be all null or all filled {members}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
    
    String [] members() default {};
    
    
}
