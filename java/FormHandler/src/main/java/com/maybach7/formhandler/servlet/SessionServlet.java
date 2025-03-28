package com.maybach7.formhandler.servlet;

import com.maybach7.formhandler.dto.CredentialsDto;
import com.maybach7.formhandler.entity.Credentials;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/session")
public class SessionServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var credentialsDto = CredentialsDto.builder()
                .login(req.getParameter("login"))
                .password(req.getParameter("password"))
                .build();

    }
}
