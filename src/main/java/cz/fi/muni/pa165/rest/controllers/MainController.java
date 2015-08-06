package cz.fi.muni.pa165.rest.controllers;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {
    
    @RequestMapping(value = "/", method = RequestMethod.GET, produces = "application/json")
    public final Map<String, String> getResources() {

        Map<String,String> resourcesMap = new HashMap<>();
        
        resourcesMap.put("products_url", "/products");
        resourcesMap.put("orders_url", "/orders");
        resourcesMap.put("users_url", "/users");
        
        return Collections.unmodifiableMap(resourcesMap);
        
    }
}
