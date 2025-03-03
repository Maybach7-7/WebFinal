package com.maybach7.formhandler.servlet;

import com.maybach7.formhandler.dto.ApplicationDto;
import com.maybach7.formhandler.entity.User;
import com.maybach7.formhandler.service.ApplicationService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;


@WebServlet("/application")
public class ApplicationServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();
        out.println("<h1>You need to post to this endpoint</h1>");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var applicationDto = ApplicationDto.builder()
                .fullName(req.getParameter("fullname"))
                .email(req.getParameter("email"))
                .phone(req.getParameter("phone"))
                .birthday(req.getParameter("birthday"))
                .gender(req.getParameter("gender"))
                .programmingLanguages(Arrays.stream(req.getParameterValues("languages")).toList())
                .biography(req.getParameter("biography"))
                .build();
        System.out.println(applicationDto);
        User user = ApplicationService.getInstance().createUser(applicationDto);
        System.out.println(user);

        resp.setContentType("text/html");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter writer = resp.getWriter();
        writer.println("<p>You've successfully submitted the application!<p>");
        writer.println("<p>" + user.toString() + "</p>");
    }
}