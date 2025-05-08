package com.maybach7.formhandler.servlet;

import com.maybach7.formhandler.dao.UserDao;
import com.maybach7.formhandler.entity.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;

@WebServlet("/admin")
public class AdminServlet extends HttpServlet {

    private final UserDao userDao = UserDao.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String authenticationHeader = req.getHeader("Authorization");
        if(authenticationHeader == null || !authenticationHeader.startsWith("Basic ")) {
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            resp.setHeader("WWW-Authenticate", "Basic realm=\"Admin\"");
            return;
        }

        String codedCredentials = authenticationHeader.substring("Basic ".length());
        byte[] bytes = Base64.getDecoder().decode(codedCredentials);

        String credentials = new String(bytes, StandardCharsets.UTF_8);
        String[] data = credentials.split(":");
        String username = data[0];
        String password = data[1];

        // затычка
        if(!("admin".equals(username) || "admin".equals(password))) {
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            resp.setHeader("WWW-Authenticate", "Basic realm=\"Admin\"");
            return;
        }

        List<User> users = userDao.findAll();
        req.setAttribute("users", users);
        req.getRequestDispatcher("admin.jsp").forward(req, resp);
    }
}