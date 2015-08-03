package revox.service;

import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by ashraf on 8/1/2015.
 */
public interface UserSecurity {
    boolean handSignUp(WebRequest request, HttpServletRequest req, HttpServletResponse res);
}
