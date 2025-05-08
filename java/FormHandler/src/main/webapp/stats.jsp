<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.*, com.maybach7.formhandler.entity.*" %>
<html>
<head>
    <title>Статистика</title>
</head>
<body>
<h1>Статистика по языкам программирования</h1>

<table>
    <tr>
        <th>Язык</th>
        <th>Количество пользователей</th>
    </tr>
    <%
        for (Map.Entry<String, Long> entry : ((Map<String, Long>) request.getAttribute("stats")).entrySet()) {
    %>
    <tr>
        <td><%= entry.getKey() %></td>
        <td><%= entry.getValue() %></td>
    </tr>
    <%
        }
    %>
</table>

<p><a href="/admin">Админ-панель</a></p>
</body>
</html>
