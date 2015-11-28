package cz.fi.muni.pa165.ws.app;

import org.springframework.ws.transport.http.support.AbstractAnnotationConfigMessageDispatcherServletInitializer;

/**
 *
 * Servlet configuration for Spring-WS
 * 
 * See http://docs.spring.io/spring-ws/docs/current/reference/html/server.html
 */
public class ServletInitializer extends AbstractAnnotationConfigMessageDispatcherServletInitializer {

    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }
    
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[]{WebServiceConfig.class};
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return null;
    }

}
