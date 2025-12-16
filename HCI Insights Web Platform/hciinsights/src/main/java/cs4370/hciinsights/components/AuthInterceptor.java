package cs4370.hciinsights.components;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import cs4370.hciinsights.services.UserService;

/**
 * This class intercepts requests that go into controllers.
 * The intercepted requests are redirected to the login page if the
 * user is not logged in.
 * The intercepter is selectively applied to different URL patterns.
 */
@Component
public class AuthInterceptor implements HandlerInterceptor {

    private UserService userService;

    @Autowired
    public AuthInterceptor(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request,
            HttpServletResponse response, Object handler) throws Exception {
        if (!userService.isAuthenticated()) {
            response.sendRedirect("/login");
            return false;
        }
        return true;
    }
    
}