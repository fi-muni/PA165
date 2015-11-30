package cz.muni.fi.pa165.restapi.config;

import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.Filter;

/**
 * Replaces web.xml file.
 * Extends the class {@link AbstractAnnotationConfigDispatcherServletInitializer} that
 * <ul>
 * <li>creates spring context specified in the class returned by {@link #getRootConfigClasses()}</li>
 * <li>initializes {@link org.springframework.web.servlet.DispatcherServlet Spring MVC dispatcher servlet} with it</li>
 * <li>maps dispatcher servlet to URL pattern returned by {@link #getServletMappings()}</li>
 * </ul>
 *
 * @author Martin Kuba makub@ics.muni.cz
 */
public class RestStartInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class<?>[]{RestSpringMvcConfig.class};
    }

    @Override
    protected String[] getServletMappings() {
        return new String[]{"/api/v1/*"};
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return null;
    }

}
