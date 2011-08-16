package twimini.controllers;

/**
 * Created by IntelliJ IDEA.
 * User: rakesh
 * Date: 14/7/11
 * Time: 2:24 PM
 * To change this template use File | Settings | File Templates.
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class AuthInterceptor extends HandlerInterceptorAdapter {
      @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        HttpSession session = request.getSession(false);
        if (session != null) {
            String userName = (String) session.getAttribute("name");

            if (userName != null) {
                return true;
            }
        }
        response.sendRedirect("/login");

        return false;
    }
}
