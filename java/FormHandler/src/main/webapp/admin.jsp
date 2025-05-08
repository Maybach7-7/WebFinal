<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.*, com.maybach7.formhandler.entity.*" %>
<html>
<head>
    <title>Админ-панель</title>
</head>
<body>
<h1>Админ-панель</h1>
<table>
    <tr>
        <th>ID</th>
        <th>Имя</th>
        <th>Email</th>
        <th>Телефон</th>
        <th>ДР</th>
        <th>Пол</th>
        <th>Языки</th>
        <th>Биография</th>
        <th>Действия</th>
    </tr>
    <%
        List<User> users = (List<User>) request.getAttribute("users");
        for (User user : users) {
    %>
    <tr>
        <td><%= user.getId() %>
        </td>
        <td><%= user.getFullName() %>
        </td>
        <td><%= user.getEmail() %>
        </td>
        <td><%= user.getPhone() %>
        </td>
        <td><%= user.getBirthday() %>
        </td>
        <td><%= user.getGender() %>
        </td>
        <td>
            <%
                List<ProgrammingLanguage> langs = user.getLanguages();
                for (int i = 0; i < langs.size(); i++) {
                    out.print(langs.get(i).getName());
                    if (i < langs.size() - 1) out.print(", ");
                }
            %>
        </td>
        <td><%= user.getBiography() %>
        </td>
        <td>
            <a href="/admin/edit?id=<%= user.getId() %>">Редактировать</a> |
            <form action="/admin/delete?id=<%= user.getId() %>" method="POST" style="display: inline;">
                <input type="submit" value="Удалить"/>
            </form>
        </td>
    </tr>
    <% } %>
</table>
<p><a href="/admin/stats">Посмотреть статистику</a></p>
</body>
</html>
