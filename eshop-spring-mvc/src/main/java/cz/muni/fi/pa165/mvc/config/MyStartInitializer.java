package cz.muni.fi.pa165.mvc.config;

import cz.muni.fi.pa165.sampledata.SampleDataLoadingFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import javax.servlet.jsp.jstl.core.Config;
import java.io.IOException;

/**
 * Replaces web.xml file, initializes Spring MVC dispatcher servlet, that in turn uses MySpringMvcConfig class
 * for Spring MVC configuration.
 *
 * @author Martin Kuba makub@ics.muni.cz
 */
public class MyStartInitializer implements WebApplicationInitializer {

    final static Logger log = LoggerFactory.getLogger(MyStartInitializer.class);

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        //create Spring beans context configured in MySpringMvcConfig.class
        AnnotationConfigWebApplicationContext ctx = new AnnotationConfigWebApplicationContext();
        ctx.setServletContext(servletContext);
        ctx.register(MySpringMvcConfig.class);
        ctx.refresh();
        servletContext.addListener(new ContextLoaderListener(ctx));

        //register Spring MVC main Dispatcher servlet
        ServletRegistration.Dynamic disp = servletContext.addServlet("dispatcher", new DispatcherServlet(ctx));
        disp.setLoadOnStartup(1);
        disp.addMapping("/");

        //register filter setting utf-8 encoding on all requests
        FilterRegistration.Dynamic encoding = servletContext.addFilter("encoding", CharacterEncodingFilter.class);
        encoding.setInitParameter("encoding", "utf-8");
        encoding.addMappingForUrlPatterns(null, false, "/*");

        //register message bundle properties also for JSTL fmt: tags which are not behind DispatcherServlet
        servletContext.setInitParameter(Config.FMT_LOCALIZATION_CONTEXT, MySpringMvcConfig.TEXTS);

        //load eShop sample data to populate the database
        try {
            ctx.getBean(SampleDataLoadingFacade.class).loadData();
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }

    }
}
