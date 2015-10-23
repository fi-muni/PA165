package cz.muni.fi.pa165.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.jsp.jstl.core.Config;

/**
 * Listener that is called on application start and stop. The place to initialize things.
 *
 * @author Martin Kuba makub@ics.muni.cz
 */
@WebListener
public class MyStartInitializer implements ServletContextListener {

    private final static Logger log = LoggerFactory.getLogger(MyStartInitializer.class);

    public void contextInitialized(ServletContextEvent sev) {

        log.info("application is starting ...");

        ServletContext servletContext = sev.getServletContext();

        //register message bundle for JSTL fmt: tags
        servletContext.setInitParameter(Config.FMT_LOCALIZATION_CONTEXT, "Texts");
    }

    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        log.info("application is stopping ...");
    }
}
