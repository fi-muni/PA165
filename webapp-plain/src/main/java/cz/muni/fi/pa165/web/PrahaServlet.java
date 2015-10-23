package cz.muni.fi.pa165.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.ResourceBundle;

/**
 * Servlet preparing data for JSP page.
 *
 * @author Martin Kuba makub@ics.muni.cz
 */
@WebServlet("/praha")
public class PrahaServlet extends HttpServlet {

    private final static Logger log = LoggerFactory.getLogger(PrahaServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("praha servlet called, forwarding to praha.jsp");
        //prepare some data to be displayed
        request.setAttribute("now", new Date());
        request.setAttribute("message", ResourceBundle.getBundle("Texts", request.getLocale()).getString("praha.message"));

        request.getRequestDispatcher("/WEB-INF/hidden-jsps/praha.jsp").forward(request, response);
    }

}
