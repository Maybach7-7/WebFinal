package com.maybach7.formhandler.servlet;

import com.maybach7.formhandler.dao.UserDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/admin/delete")
public class DeleteApplicationServlet extends HttpServlet {

    private final UserDao userDao = UserDao.getInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));

        userDao.deleteById(id);
        resp.sendRedirect("/admin");
    }
}