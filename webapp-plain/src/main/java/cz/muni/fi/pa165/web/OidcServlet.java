package cz.muni.fi.pa165.web;

import com.fasterxml.jackson.databind.JsonNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Random;

/**
 * A servlet for OAuth2 login using Google account.
 * <p/>
 * This application must be registered at Google APIs Console of the developer https://code.google.com/apis/console/
 * to receive client_id and client_secret. The redirect_uri must be registered there too.
 *
 * @author Martin Kuba makub@ics.muni.cz
 */
@WebServlet("/google/*")
public class OidcServlet extends HttpServlet {

    private final static Logger log = LoggerFactory.getLogger(OidcServlet.class);

    //constants
    private final static String CLIENT_ID = "487495688780-ueip6ipilfbnvhfvon4cmc6pa764dgf2.apps.googleusercontent.com";
    private final static String CLIENT_SECRET = "qwlcvnsmJT7YnVcW0xuyOGVw";
    private final static String REDIRECT_URI = "http://localhost:8080/webapp-plain/google/auth";
    private final static String METADATA_URL = "https://accounts.google.com/.well-known/openid-configuration";
    private final static String SCOPES = "openid profile email";

    //URLs read from metadata URL
    private String authorization_endpoint;
    private String token_endpoint;
    private String userinfo_endpoint;

    private Random random = new Random();

    @Override
    public void init() {
        loadMetadata();
    }

    private void loadMetadata() {
        RestTemplate restTemplate = new RestTemplate();
        JsonNode metadata = restTemplate.getForObject(METADATA_URL, JsonNode.class);
        authorization_endpoint = metadata.path("authorization_endpoint").asText();
        token_endpoint = metadata.path("token_endpoint").asText();
        userinfo_endpoint = metadata.path("userinfo_endpoint").asText();
        log.info("Loaded metadata for OpenID Connect Provider {}",metadata.path("issuer").asText());
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        if ("/login".equals(req.getPathInfo())) {
            /*
             * Step 1 - initiate OAuth2 "Authorization Code Grant" flow
             */

            //state is a random value protecting against XSRF
            String state = Integer.toString(random.nextInt(Integer.MAX_VALUE));
            req.getSession(true).setAttribute("state", state);

            //redirect to Google to ask for permission on email
            String loginRedirectURL = authorization_endpoint + "?" +
                    "response_type=code"
                    + "&client_id=" + urlEncode(CLIENT_ID)
                    + "&redirect_uri=" + urlEncode(REDIRECT_URI)
                    + "&state=" + urlEncode(state)
                    + "&scope=" + urlEncode(SCOPES);
            resp.sendRedirect(loginRedirectURL);
            log.debug("send redirect to Google to initiate OAuth2 flow");

        } else if ("/auth".equals(req.getPathInfo())) {
            /*
             * Step 2 - process Google authorization
             */

            //check state for XSRF attack
            String state = req.getParameter("state");
            String state1 = (String) req.getSession(true).getAttribute("state");
            if (state == null || !state.equals(state1)) {
                resp.sendError(HttpServletResponse.SC_FORBIDDEN, "state does not match, probably a XSRF attack");
                return;
            }

            //get authorization code (expressing user consent)
            String code = req.getParameter("code");
            if (code == null) {
                resp.sendError(HttpServletResponse.SC_FORBIDDEN, "code not present");
                return;
            }
            log.debug("got code from Google, going to exchange it for accessToken");

            //exchange authorization code for access token
            RestTemplate restTemplate = new RestTemplate();
            MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
            map.set("client_id", CLIENT_ID);
            map.set("client_secret", CLIENT_SECRET);
            map.set("redirect_uri", REDIRECT_URI);
            map.set("code", code);
            map.set("grant_type", "authorization_code");
            JsonNode jsonNode = restTemplate.postForObject(token_endpoint, map, JsonNode.class);
            String accessToken = jsonNode.path("access_token").asText();
            String expires = jsonNode.path("expires_in").asText();
            log.debug("received accessToken {} from Google, it expires in {} seconds", accessToken, expires);

            //use token for getting user data
            log.debug("using accessToken to read user data from Google");
            JsonNode userData = restTemplate.getForObject(userinfo_endpoint + "?access_token=" + urlEncode(accessToken), JsonNode.class);
            log.debug("user data: {}",userData);
            String userId = userData.path("sub").asText();
            String userEmail = userData.path("email").asText();
            String userName = userData.path("name").asText();
            String userPicture = userData.path("picture").asText();
            UserInfo userInfo = new UserInfo(userId, userEmail, userName, userPicture);
            log.info("got user {}", userInfo);

            //store UserInfo to session
            req.getSession(true).setAttribute("user", userInfo);

            //redirect to home page
            resp.sendRedirect(req.getContextPath() + "/home");
        }
    }

    @SuppressWarnings("unused")
    public static class UserInfo {
        private final String id;
        private final String email;
        private final String fullname;
        private final String pictureURL;

        UserInfo(String id, String email, String fullname, String pictureURL) {
            this.id = id;
            this.email = email;
            this.fullname = fullname;
            this.pictureURL = pictureURL;
        }

        public String getId() {
            return id;
        }

        public String getEmail() {
            return email;
        }

        public String getFullname() {
            return fullname;
        }

        public String getPictureURL() {
            return pictureURL;
        }

        @Override
        public String toString() {
            return "UserInfo{" +
                    "id='" + id + '\'' +
                    ", email='" + email + '\'' +
                    ", fullname='" + fullname + '\'' +
                    '}';
        }
    }

    private static String urlEncode(String s) {
        try {
            return URLEncoder.encode(s, "utf-8");
        } catch (UnsupportedEncodingException e) {
            throw new Error("utf-8 unknown", e);
        }
    }
}
