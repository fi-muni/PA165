package cz.muni.fi.pa165.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/**
 * Example of a form.
 *
 * @author Martin Kuba makub@ics.muni.cz
 */

@WebServlet(PharmacyServlet.PHARMACY_URL)
public class PharmacyServlet extends HttpServlet {

    // a constant for servlet path in URL
    static final String PHARMACY_URL = "/pharmacy";

    private final static Logger log = LoggerFactory.getLogger(PharmacyServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("pharmacy servlet called by HTTP GET");
        request.getRequestDispatcher("/WEB-INF/hidden-jsps/pharmacy.jsp").forward(request, response);
    }


    @Override
    @SuppressWarnings("unchecked")
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String name = request.getParameter("name");
        String amount = request.getParameter("amount");
        String vendor = request.getParameter("vendor");
        log.debug("pharmacy servlet called by HTTP POST, name={} amount={} vendor={}",name,amount,vendor);

        //add the entered data to a list held in a servlet context attribute
        ArrayList<Drug> drugs = (ArrayList<Drug>) getServletContext().getAttribute("drugs");
        drugs.add(new Drug(name,amount,vendor));

        //redirect-after-post to clear browser history
        response.sendRedirect(request.getContextPath() + PHARMACY_URL);
    }

    @Override
    public void init() {
        log.debug("preparing example data");
        ArrayList<Drug> drugs = new ArrayList<>();
        getServletContext().setAttribute("drugs", drugs);
        drugs.add(new Drug("Celaskon","500mg","Zentiva"));
        drugs.add(new Drug("Aspirin", "30mg","Bayer"));
    }

    /**
     * A simple javabean for keeping data.
     */
    @SuppressWarnings("unused")
    public class Drug {

        private String name;
        private String amount;
        private String vendor;

        public Drug(String name, String amount,String vendor) {
            this.name = name;
            this.amount = amount;
            this.vendor = vendor;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public String getVendor() {
            return vendor;
        }

        public void setVendor(String vendor) {
            this.vendor = vendor;
        }
    }
}
