package vn.iot.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.servlet.HandlerInterceptor;
import vn.iot.enity.UserEntity;

public class AuthInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String uri = request.getRequestURI();
        HttpSession session = request.getSession(false);

        UserEntity user = null;
        if (session != null) {
            user = (UserEntity) session.getAttribute("user");
        }

        if (user == null) {
            if (uri.startsWith("/login") || uri.startsWith("/register")) {
                return true;
            }
            response.sendRedirect("/login");
            return false;
        }
        if (uri.startsWith("/admin")) {
            if (!"Admin".equals(user.getRole())) {
                response.sendRedirect("/user/home");
                return false;
            }
        } else if (uri.startsWith("/user")) {
            if (!"User".equals(user.getRole())) {
                response.sendRedirect("/admin/categories");
                return false;
            }
        }
        return true;
    }
}
