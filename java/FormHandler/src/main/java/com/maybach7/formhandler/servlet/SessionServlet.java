package com.maybach7.formhandler.servlet;

import com.maybach7.formhandler.dto.LoginDto;
import com.maybach7.formhandler.exception.LoginException;
import com.maybach7.formhandler.exception.ValidationException;
import com.maybach7.formhandler.service.LoginService;
import com.maybach7.formhandler.util.CookiesUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/session")
public class SessionServlet extends HttpServlet {

    private final static LoginService loginService = LoginService.getInstance();

    // POST запрос исспользуется при авторизации
    // и выдаёт session_id
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var credentialsDto = LoginDto.builder()
                .login(req.getParameter("login"))
                .password(req.getParameter("password"))
                .build();

        try {
            var session = loginService.login(credentialsDto);
            CookiesUtil.setSessionCookie(resp, session);
            CookiesUtil.clearCookies(req, resp);

            resp.sendRedirect(req.getContextPath() + "/application");
        } catch (ValidationException exc) {
            req.getSession().setAttribute("errors", exc.getErrors());
            resp.sendRedirect(req.getContextPath() + "/login");
        } catch (LoginException exc) {
            req.getSession().setAttribute("errors", exc.getErrors());
            resp.sendRedirect(req.getContextPath() + "/login");
        }
    }
}