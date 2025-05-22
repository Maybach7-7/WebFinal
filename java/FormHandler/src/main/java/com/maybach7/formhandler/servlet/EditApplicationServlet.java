package com.maybach7.formhandler.servlet;

import com.maybach7.formhandler.dao.UserDao;
import com.maybach7.formhandler.dto.ApplicationDto;
import com.maybach7.formhandler.entity.User;
import com.maybach7.formhandler.exception.ValidationException;
import com.maybach7.formhandler.service.ApplicationService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.text.StringEscapeUtils;

import java.io.IOException;
import java.util.Arrays;

@WebServlet("/admin/edit")
public class EditApplicationServlet extends HttpServlet {

    private final UserDao userDao = UserDao.getInstance();
    private final ApplicationService applicationService = ApplicationService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        User user = userDao.findById(id).get();

        req.setAttribute("user", user);
        req.getRequestDispatcher("/edit.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        var applicationDto = ApplicationDto.builder()
                .fullName(StringEscapeUtils.escapeHtml4(req.getParameter("fullname")))
                .email(StringEscapeUtils.escapeHtml4(req.getParameter("email")))
                .phone(StringEscapeUtils.escapeHtml4(req.getParameter("phone")))
                .birthday(StringEscapeUtils.escapeHtml4(req.getParameter("birthday")))
                .gender(StringEscapeUtils.escapeHtml4(req.getParameter("gender")))
                .programmingLanguages(Arrays.stream(req.getParameterValues("languages")).toList())
                .biography(StringEscapeUtils.escapeHtml4(req.getParameter("biography")))
                .build();

        int id = Integer.parseInt(req.getParameter("id"));

        try {
            applicationService.updateUser(applicationDto, id);
        } catch (ValidationException e) {
            throw new RuntimeException(e);
        }
        resp.sendRedirect("/admin");
    }
}