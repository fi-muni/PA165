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
 * Servlet for home page. Hides the JSP from direct access, as anything in the WEB-INF directory
 * is not accessible from outside.
 *
 * @author Martin Kuba makub@ics.muni.cz
 */
@WebServlet("/home")
public class HomeServlet extends HttpServlet {

    private final static Logger log = LoggerFactory.getLogger(HomeServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("home servlet called, forwarding to home.jsp");
        request.getRequestDispatcher("/WEB-INF/hidden-jsps/home.jsp").forward(request, response);
    }

}
