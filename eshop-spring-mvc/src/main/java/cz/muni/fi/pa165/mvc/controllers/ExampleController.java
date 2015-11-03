package cz.muni.fi.pa165.mvc.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;

/**
 * Example controller showing as much features as possible.
 *
 * @author Martin Kuba makub@ics.muni.cz
 */
@Controller
@RequestMapping("/example")
public class ExampleController {

    final static Logger log = LoggerFactory.getLogger(ExampleController.class);

    @Autowired
    private MessageSource messageSource; //resource bundle provided by Spring

    /**
     * Example method. Shows what is possible to receive from SpringMVC as method parameters,
     * how to pass values through Model to view, and how to do redirects.
     * <p>
     * See  http://docs.spring.io/spring/docs/current/spring-framework-reference/html/mvc.html#mvc-ann-arguments for details.
     * Call with <code>/example/foo/1/bar99?b=333&redir=false</code>.
     *
     * @return view name
     */
    @RequestMapping(value = "/foo/{a}/{r1:[a-z]+}{r2:\\d+}", method = RequestMethod.GET)
    public String foo(
            @PathVariable int a, //parsed from URL from the place of {a}
            @PathVariable String r1, // part named r1 from regex {r1:[a-z]+}{r2:\d+}
            @PathVariable int r2, // part named r2 from regex {r1:[a-z]+}{r2:\d+}
            @RequestParam long b, // URL parameter "b", required
            @RequestParam(defaultValue = "false") boolean redir, // URL parameter "redir", optional, used to ask for a redirect
            Locale locale, // request locale
            HttpMethod httpMethod, //request method
            Principal user, //authenticated user
            @RequestHeader("User-agent") String userAgent, // HTTP header
            @CookieValue(value = "JSESSIONID", required = false) Cookie session_cookie, // HTTP cookie
            Model model, // for sending values to view
            UriComponentsBuilder uriBuilder, // for building URLs
            RedirectAttributes redirectAttributes, //for flash attributes on redirects
            HttpServletRequest req, HttpServletResponse res //not recommended, makes unit testing difficult
    ) {
        log.debug("a={}, r1={}, r2={}, b={}, redir={}", a, r1, r2, b, redir);
        log.debug("locale={}, httpMethod={}, user={}, userAgent={}", locale, httpMethod, user, userAgent);
        if (!redir) {
            //pass data to JSP page through model (values for @PathVariable are passed automatically)
            model.addAttribute("b", b);
            model.addAttribute("locale", locale);
            model.addAttribute("httpMethod", httpMethod);
            model.addAttribute("user", user);
            model.addAttribute("userAgent", userAgent);
            model.addAttribute("session_cookie", session_cookie);
            //no redirect, simply return the name of the JSP page, will be expanded to /WEB-INF/jsp/example.jsp
            return "example";
        } else {
            //do redirect with displaying a message after the redirect

            //get message from resource bundle, fill in parameters
            String time = ZonedDateTime.now().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.FULL).withLocale(locale));
            String message = messageSource.getMessage("example.msg_with_params", new Object[]{time}, locale);
            //flash attribute exists only on the first request after redirect, good for displaying temporary messages
            redirectAttributes.addFlashAttribute("alert_info", message);
            //build a URL for redirect
            String url =
                    uriBuilder.path("/example/foo/{a}/{r1}{r2}") // application-relative URL with template variables
                            .queryParam("b", b + 1) // add query parameter
                            .buildAndExpand(a + 1, r1 + "xxx", r2 + 1) // replace template variables with values in given order
                            .encode().toUriString();
            return "redirect:" + url;
        }
    }

    @RequestMapping("/bar")
    public String bar(@RequestParam String c,
                      @RequestParam int d,
                      @RequestParam boolean e,
                      Model model
                      ) {
        model.addAttribute("c", c);
        model.addAttribute("d", d);
        model.addAttribute("e", e);
        return "bar";
    }
}
