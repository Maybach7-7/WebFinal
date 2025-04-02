package com.maybach7.formhandler.servlet;

import com.maybach7.formhandler.dto.ApplicationDto;
import com.maybach7.formhandler.entity.Credentials;
import com.maybach7.formhandler.entity.User;
import com.maybach7.formhandler.exception.ValidationException;
import com.maybach7.formhandler.service.ApplicationService;
import com.maybach7.formhandler.service.AuthService;
import com.maybach7.formhandler.service.CredentialsService;
import com.maybach7.formhandler.service.HashingService;
import com.maybach7.formhandler.util.CookiesUtil;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;


@WebServlet("/application")
public class ApplicationServlet extends HttpServlet {

    private static final CredentialsService credentialsService = CredentialsService.getInstance();

    private static final List<String> singleFields = Arrays.asList(
            "fullname",
            "email",
            "phone",
            "birthday",
            "gender",
            "biography"
    );

    private static final List<String> multipleFields = Arrays.asList(
            "languages"
    );

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        RequestDispatcher dispatcher = req.getRequestDispatcher("form.jsp");

        String sessionId = CookiesUtil.getCookie(req, "session_id").orElse(null);
        if(sessionId != null) {

        } else {
            HttpSession session = req.getSession();
            if (session.getAttribute("errors") != null) {
                req.setAttribute("errors", session.getAttribute("errors"));
                session.removeAttribute("errors");
            }
            if (session.getAttribute("login") != null && session.getAttribute("password") != null) {
                System.out.println("Login in session: " + session.getAttribute("login"));
                req.setAttribute("login", session.getAttribute("login"));
                session.removeAttribute("login");

                req.setAttribute("password", session.getAttribute("password"));
                session.removeAttribute("password");
            }
            session.invalidate();   // дальше пусть пользователь регается

            // Мы перенаправляем сюда POST-запрос, предварительно записав в ответ необходимые Cookies
            // Здесь мы читаем эти Cookies из запроса, устанавливаем в запросе аттрибуты для каждого поля,
            // имея в них имя Cookie, и его значение.
            // Затем этот запрос направляется в form.jsp с помощью RequestDispatcher, откуда оно будет передано
            // обратно изначальному клиенту
            for (var field : singleFields) {
                CookiesUtil.getCookie(req, field).ifPresent(value -> req.setAttribute(field, value));
            }

            for (var field : multipleFields) {
                CookiesUtil.getCookieArray(req, field).ifPresent(value -> req.setAttribute(field, String.join(",", value)));
            }
        }
        // qbjiuonfly
        // 24*nE|E,Jg

        dispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        resp.setContentType("text/html");
        resp.setCharacterEncoding(StandardCharsets.UTF_8.name());
        var applicationDto = ApplicationDto.builder()
                .fullName(req.getParameter("fullname"))
                .email(req.getParameter("email"))
                .phone(req.getParameter("phone"))
                .birthday(req.getParameter("birthday"))
                .gender(req.getParameter("gender"))
                .programmingLanguages(Arrays.stream(req.getParameterValues("languages")).toList())
                .biography(req.getParameter("biography"))
                .build();
        try {
            System.out.println(applicationDto);
            User user = ApplicationService.getInstance().createUser(applicationDto);
            System.out.println(user);

            // Установка Cookies для автозаполнения формы
            int year = 60 * 60 * 24 * 365;
            for (var field : singleFields) {
                String value = req.getParameter(field);
                CookiesUtil.setCookie(resp, field, value, year);
            }
            for (var field : multipleFields) {
                String[] values = req.getParameterValues(field);
                CookiesUtil.setCookieArray(resp, field, values, year);
            }
            String login = AuthService.createLogin();
            String password = AuthService.createPassword();
            byte[] salt = HashingService.getSalt();
            byte[] hashedPassword = HashingService.getHash(password, salt);
            Credentials credentials = Credentials.builder()
                            .userId(user.getId())
                            .login(login)
                            .password(HashingService.toString(hashedPassword))
                            .salt(HashingService.toString(salt))
                            .build();

            credentialsService.createCredentials(credentials);

            req.getSession().setAttribute("login", login);
            req.getSession().setAttribute("password", password);
            resp.sendRedirect(req.getContextPath() + "/application");
        } catch (ValidationException exc) {      // здесь необходимо передать список ошибок в JSP и обработать там

            for (var field : singleFields) {
                String value = req.getParameter(field);
                CookiesUtil.setCookie(resp, field, value);
            }
            for (var field : multipleFields) {
                String[] values = req.getParameterValues(field);
                CookiesUtil.setCookieArray(resp, field, values);
            }

            System.out.println("Произошла ошибка в валидации");
            req.getSession().setAttribute("errors", exc.getErrors());
            resp.sendRedirect(req.getContextPath() + "/application");
        }
    }
}