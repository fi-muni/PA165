package cz.muni.fi.pa165.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet preparing data for JSP page.
 *
 * @author Martin Kuba makub@ics.muni.cz
 */
@WebServlet("/podoli")
public class PodoliServlet extends HttpServlet {

    private final static Logger log = LoggerFactory.getLogger(PodoliServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("podoli servlet called, forwarding to podoli.jsp");
        request.getRequestDispatcher("/WEB-INF/hidden-jsps/podoli.jsp").forward(request, response);
    }

}
