package cz.muni.fi.pa165.web;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Filter which adds an attribute with the current year to every request.
 *
 * @author Martin Kuba makub@ics.muni.cz
 */
@WebFilter("/*")
public class CurrentYearFilter implements Filter {


    @Override
    public void doFilter(ServletRequest r, ServletResponse s, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) r;
        HttpServletResponse response = (HttpServletResponse) s;
        request.setAttribute("currentyear",new SimpleDateFormat("yyyy",request.getLocale()).format(new Date()));
        chain.doFilter(request, response);
    }

    @Override
    public void init(FilterConfig filterConfig) {
    }

    @Override
    public void destroy() {
    }
}
