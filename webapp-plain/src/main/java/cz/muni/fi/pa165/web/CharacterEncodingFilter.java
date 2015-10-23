package cz.muni.fi.pa165.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Filter that sets character encoding of all incoming requests to UTF-8.
 * Otherwise default is iso-8859-1 which does not contain Czech characters.
 *
 * @author Martin Kuba makub@ics.muni.cz
 */
@WebFilter("/*")
public class CharacterEncodingFilter implements Filter {

    private final static Logger log = LoggerFactory.getLogger(CharacterEncodingFilter.class);

    public void doFilter(ServletRequest r, ServletResponse s, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) r;
        HttpServletResponse response = (HttpServletResponse) s;
        request.setCharacterEncoding("utf-8");
        log.trace("doFilter {}", request.getRequestURL());
        filterChain.doFilter(request, response);
    }

    public void init(FilterConfig filterConfig) {
        log.debug("filter initialized ...");
    }

    public void destroy() {
    }
}
