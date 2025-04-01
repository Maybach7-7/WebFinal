package com.maybach7.formhandler.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HttpSession session = req.getSession();
        if(session.getAttribute("errors") != null) {
            req.setAttribute("errors", session.getAttribute("errors"));
            session.removeAttribute("errors");
        }
        session.invalidate();

        req.getRequestDispatcher("login.jsp").forward(req, resp);
    }
}