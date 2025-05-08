<%@ page import="java.util.Arrays" %>
<%@ page import="com.maybach7.formhandler.validator.InputError" %>
<%@ page import="java.util.List" %>
<%@ page import="com.maybach7.formhandler.util.CookiesUtil" %>
<%@ page import="com.maybach7.formhandler.entity.Gender" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Application</title>
    <link rel="stylesheet" type="text/css" href="http://localhost/static/form/style.css">
</head>
<body>

<header class="header">
    <a href="/admin"><button class="navbar-button">Я админ</button></a>
    <%
        if(CookiesUtil.getCookie(request, "session_id").isPresent()) {
     %>
            <button class="navbar-button">Выйти</button>
    <%
        } else {
    %>
            <a href="/login"><button class="login-button">Войти</button></a>
    <%
        }
    %>
</header>

<div class="popup-content">
    <h3>Форма для розыгрыша</h3><br>

    <form id="form" action="" method="POST">
        <table>
            <%
                boolean hasErrors = false;
                List<InputError> errors = (List<InputError>) request.getAttribute("errors");
                if (errors != null) {
                    hasErrors = true;
                    for (var error : errors) {
            %>
            <p style="color:red; padding: 10px"><%=error.getMessage()%></p>
            <%
                    }
                }
            %>
            <tr>
                <td><label for="fullname">ФИО</label></td>
                <td><input name="fullname" id="fullname" type="text" required
                           value="<%= request.getAttribute("fullname") != null ? request.getAttribute("fullname") : "" %>"
                    <%= hasErrors && errors.stream().anyMatch(error -> error.getId().equals("fullname")) ? "style=\"border: 2px solid red;\"" : ""%>>
                </td>
            </tr>
            <tr>
                <td><label for="email">Email</label></td>
                <td><input name="email" id="email" type="email" required
                           value="<%= request.getAttribute("email") != null ? request.getAttribute("email") : "" %>"
                    <%= hasErrors && errors.stream().anyMatch(error -> error.getId().equals("email")) ? "style=\"border: 2px solid red;\"" : ""%>>
                </td>
            </tr>
            <tr>
                <td><label for="phone">Телефон</label></td>
                <td><input id="phone" name="phone" type="tel" required
                           value="<%= request.getAttribute("phone") != null ? request.getAttribute("phone") : "" %>"
                    <%= hasErrors && errors.stream().anyMatch(error -> error.getId().equals("phone")) ? "style=\"border: 2px solid red;\"" : ""%>>
                </td>
            </tr>
            <tr>
                <td><label for="birthday">Дата рождения</label></td>
                <td><input id="birthday" name="birthday" type="date" required
                           value="<%= request.getAttribute("birthday") != null ? request.getAttribute("birthday") : "" %>">
                </td>
            </tr>

            <tr>
                <td><label>Пол</label></td>
                <td>
                    <div class="radio-group">
                        <input type="radio" required name="gender" value="male" id="male"
                                <%= request.getAttribute("gender")!=null && "male".equalsIgnoreCase(request.getAttribute("gender").toString()) ? "checked" : ""%> />
                        <label for="male">Мужской</label>
                    </div>

                    <div class="radio-group">
                        <input type="radio" required name="gender" value="female" id="female"
                                <%= request.getAttribute("gender")!=null && "female".equalsIgnoreCase(request.getAttribute("gender").toString()) ? "checked" : ""%> />
                        <label for="female">Женский</label>
                    </div>
                </td>
            </tr>

            <tr>
                <td>
                    <label for="languages">Укажите любимые языки:</label>
                </td>
                <td>
                    <select id="languages" name="languages" multiple="multiple" required>
                        <%
                            String selectedLanguagesRaw = (String) request.getAttribute("languages");
                            String[] selectedLanguages = selectedLanguagesRaw != null ? selectedLanguagesRaw.split(",") : new String[0];
                            String[] allLanguages = {"Pascal", "C", "C++", "JavaScript", "PHP", "Python", "Java", "Haskel", "Clojure", "Prolog", "Scala"};
                            for (String lang : allLanguages) {
                                boolean isSelected = Arrays.asList(selectedLanguages).contains(lang.toLowerCase());
                        %>
                        <option value="<%= lang.toLowerCase() %>" <%= isSelected ? "selected" : "" %>><%= lang %>
                        </option>
                        <%
                            }
                        %>
                    </select>
                </td>
            </tr>
            <tr>
                <td><label for="biography">Биография</label></td>
                <td><textarea id="biography" name="biography"
                              placeholder="Я родился в Москве в 70-м на краю города"><%= request.getAttribute("biography") != null ? request.getAttribute("biography") : "" %></textarea>
                </td>
            </tr>

            <tr>
                <td colspan="2">
                    <div class="checkbox-group">
                        <input type="checkbox" name="checkbox" id="checkbox">
                        <label for="checkbox">Готов стать миллионером</label>
                    </div>
                </td>
            </tr>
        </table>

        <div id="button-container">
            <button type="submit">Отправить</button>
        </div>
        <div id="result"></div>
    </form>
    <% if (request.getAttribute("login") != null) {%>
    <p>Ваш логин: ${login}</p>
    <%}%>

    <% if (request.getAttribute("password") != null) {%>
    <p>Ваш пароль: ${password}</p>
    <%}%>
</div>
</body>
</html>