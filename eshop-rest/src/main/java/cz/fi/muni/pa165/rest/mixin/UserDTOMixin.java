package cz.fi.muni.pa165.rest.mixin;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 *
 * @author brossi
 */
@JsonIgnoreProperties({ "passwordHash"})
public class UserDTOMixin {
    
}
