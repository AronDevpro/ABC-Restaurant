package authentication;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.User;

import java.io.IOException;

@WebFilter("/admin/*")
public class AdminAuthenticationFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        HttpSession session = req.getSession(false);
        // Check if user is authenticated
        if (req.getSession().getAttribute("user") == null) {
            // User is not authenticated, redirect to login page
            resp.sendRedirect(req.getContextPath() + "/login?error=You need to log in to access this page.");
        } else {
            User user = (User) req.getSession().getAttribute("user");
            String accountType = user.getAccountType();
            if ("Admin".equals(accountType)) {
                chain.doFilter(request, response);
            } else {
                session.invalidate();
                resp.sendRedirect(req.getContextPath() + "/login?error=Unauthorized Access! Your session has been terminated.");
            }

        }
    }

}
