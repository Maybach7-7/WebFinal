package com.maybach7.formhandler.servlet;

import com.maybach7.formhandler.dao.UserDao;
import com.maybach7.formhandler.entity.ProgrammingLanguage;
import com.maybach7.formhandler.entity.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@WebServlet("/admin/stats")
public class StatsServlet extends HttpServlet {

    private final UserDao userDao = UserDao.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<User> users = userDao.findAll();

        Map<String, Long> stats = users.stream()
                .flatMap(user -> user.getLanguages().stream())
                .collect(Collectors.groupingBy(ProgrammingLanguage::getName, Collectors.counting()));   // подсчитываем количество каждого языка программирования

        req.setAttribute("stats", stats);
        req.getRequestDispatcher("/stats.jsp").forward(req, resp);
    }
}