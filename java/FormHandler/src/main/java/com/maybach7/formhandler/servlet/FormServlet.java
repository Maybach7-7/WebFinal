package com.maybach7.formhandler.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.maybach7.formhandler.dao.SessionDao;
import com.maybach7.formhandler.dto.ApplicationDto;
import com.maybach7.formhandler.entity.Credentials;
import com.maybach7.formhandler.entity.ProgrammingLanguage;
import com.maybach7.formhandler.entity.Session;
import com.maybach7.formhandler.entity.User;
import com.maybach7.formhandler.exception.InvalidSessionException;
import com.maybach7.formhandler.exception.ValidationException;
import com.maybach7.formhandler.service.*;
import com.maybach7.formhandler.util.CookiesUtil;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import org.apache.commons.text.StringEscapeUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Map;


@WebServlet("/form")
public class FormServlet extends HttpServlet {

    private final CredentialsService credentialsService = CredentialsService.getInstance();
    private final SessionService sessionService = SessionService.getInstance();
    private final SessionDao sessionDao = SessionDao.getInstance();
    private final ApplicationService applicationService = ApplicationService.getInstance();
    private final ObjectMapper objectMapper = new ObjectMapper();

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
        if (sessionId != null) {

            try {
                User user = sessionService.getUserBySessionId(sessionId);
                System.out.println("Найденный пользователь: " + user);
                req.setAttribute("fullname", user.getFullName());
                req.setAttribute("email", user.getEmail());
                req.setAttribute("phone", user.getPhone());
                req.setAttribute("birthday", user.getBirthday());
                req.setAttribute("gender", user.getGender());
                req.setAttribute("biography", user.getBiography());
                var temp = user.getLanguages();
                String[] temp2 = temp.stream()
                        .map(ProgrammingLanguage::getName)
                        .toArray(String[]::new);
                System.out.println(Arrays.toString(temp2));
                System.out.println(String.join(",", temp2));
                req.setAttribute("languages", String.join(",", temp2));

            } catch (InvalidSessionException exc) {
                CookiesUtil.clearCookies(req, resp);
                dispatcher.forward(req, resp);
            }
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

        dispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        resp.setContentType("text/html");
        resp.setCharacterEncoding(StandardCharsets.UTF_8.name());

        boolean isJsonRequest = req.getContentType().contains("application/json");
        ApplicationDto applicationDto = null;
        if (isJsonRequest) {
            applicationDto = objectMapper.readValue(req.getInputStream(), ApplicationDto.class);
        } else {
            applicationDto = ApplicationDto.builder()
                    .fullName(StringEscapeUtils.escapeHtml4(req.getParameter("fullname")))
                    .email(StringEscapeUtils.escapeHtml4(req.getParameter("email")))
                    .phone(StringEscapeUtils.escapeHtml4(req.getParameter("phone")))
                    .birthday(StringEscapeUtils.escapeHtml4(req.getParameter("birthday")))
                    .gender(StringEscapeUtils.escapeHtml4(req.getParameter("gender")))
                    .programmingLanguages(Arrays.stream(req.getParameterValues("languages")).toList())
                    .biography(StringEscapeUtils.escapeHtml4(req.getParameter("biography")))
                    .build();
        }

        String sessionId = CookiesUtil.getCookie(req, "session_id").orElse(null);
        boolean hasSessionId = sessionId != null;

        try {
            if (hasSessionId) {
                Session session = sessionDao.findBySessionId(sessionId);
                if (session == null) {
                    CookiesUtil.clearCookies(req, resp);
                    resp.sendRedirect("/form");
                    return;
                } else {
                    applicationService.updateUser(applicationDto, session.getUserId());
                    if(isJsonRequest) {
                        resp.setContentType("application/json");
                        resp.setStatus(HttpServletResponse.SC_ACCEPTED);
                        return;
                    }
                }
            } else {
                System.out.println(applicationDto);
                System.out.println("Собираемся делать валидацию перед сохранением в бд");
                User user = applicationService.createUser(applicationDto);
                System.out.println(user);

                String login = AuthService.createLogin();
                String password = AuthService.createPassword();
                // сохраняем логин и пароль в БД
                byte[] salt = HashingService.getSalt();
                byte[] hashedPassword = HashingService.getHash(password, salt);
                Credentials credentials = Credentials.builder()
                        .userId(user.getId())
                        .login(login)
                        .password(HashingService.toString(hashedPassword))
                        .salt(HashingService.toString(salt))
                        .build();

                credentialsService.createCredentials(credentials);

                if(isJsonRequest) {
                    resp.setContentType("application/json");
                    resp.setCharacterEncoding(StandardCharsets.UTF_8.name());
                    resp.setStatus(HttpServletResponse.SC_OK);
                    var responseJson = Map.of(
                            "login", login,
                            "password", password
                    );
                    objectMapper.writeValue(resp.getWriter(), responseJson);
                    return;
                } else {
                    req.getSession().setAttribute("login", login);
                    req.getSession().setAttribute("password", password);
                }
            }

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
            resp.sendRedirect(req.getContextPath() + "/form");
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
            resp.sendRedirect(req.getContextPath() + "/form");
        }
    }
}