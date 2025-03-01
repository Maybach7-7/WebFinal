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
import java.util.Arrays;


@WebServlet("/application")
public class ApplicationServlet extends HttpServlet {

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
        User user = ApplicationService.getInstance().createUser(applicationDto);
        System.out.println(user);
    }
}