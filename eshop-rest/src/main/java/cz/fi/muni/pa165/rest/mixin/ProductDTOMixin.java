package cz.fi.muni.pa165.rest.mixin;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * This class shows an example of Jackson mix-ins in case we do not want to
 * modify one DTO in the API layer
 *
 * @author brossi
 */
@JsonIgnoreProperties({"imageMimeType"})
public abstract class ProductDTOMixin {

    // @JsonIgnore and @JsonProperty can be used as well:
    //  @JsonIgnore  private String imageMimeType;
}
