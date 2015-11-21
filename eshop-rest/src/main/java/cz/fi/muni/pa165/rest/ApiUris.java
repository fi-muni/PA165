package cz.fi.muni.pa165.rest;

/**
 * Represents the entry points for the API
 * this list can be increased so that it contains all the 
 * other URIs also for the sub-resources so that it can 
 * reused globally from all the controllers
 * 
 * @author brossi
 */
public abstract class ApiUris {
    public static final String ROOT_URI_PRODUCTS   = "/products"; 
    public static final String ROOT_URI_USERS      = "/users";
    public static final String ROOT_URI_ORDERS     = "/orders";
    public static final String ROOT_URI_CATEGORIES = "/categories";  
}
