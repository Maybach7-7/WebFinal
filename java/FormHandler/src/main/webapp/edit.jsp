<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.maybach7.formhandler.entity.*, java.util.*" %>
<%
    User user = (User) request.getAttribute("user");
    List<ProgrammingLanguage> allLangs = Arrays.asList(ProgrammingLanguage.values());
%>

<html>
<head>
    <title>Редактировать пользователя</title>
</head>
<body>
<h1>Редактирование пользователя</h1>
<form method="post" action="/admin/edit?id=<%= user.getId()%>">

    <label>ФИО: <input type="text" name="fullname" value="<%= user.getFullName() %>"/></label><br/>
    <label>Email: <input type="email" name="email" value="<%= user.getEmail() %>"/></label><br/>
    <label>Телефон: <input type="text" name="phone" value="<%= user.getPhone() %>"/></label><br/>
    <label>Дата рождения: <input type="date" name="birthday" value="<%= user.getBirthday() %>"/></label><br/>

    <label>Пол:</label>
    <label><input type="radio" name="gender" value="MALE" <%= user.getGender() == Gender.MALE ? "checked" : "" %>>
        Мужской</label>
    <label><input type="radio" name="gender" value="FEMALE" <%= user.getGender() == Gender.FEMALE ? "checked" : "" %>>
        Женский</label><br/>

    <label>Языки программирования:</label><br/>
    <%
        for (ProgrammingLanguage lang : allLangs) {
            boolean selected = user.getLanguages().contains(lang);
    %>
    <label>
        <input type="checkbox" name="languages" value="<%= lang.name() %>" <%= selected ? "checked" : "" %>>
        <%= lang.getName() %>
    </label><br/>
    <%
        }
    %>

    <label>Биография:<br/>
        <textarea name="biography" rows="5" cols="40"><%= user.getBiography() %></textarea>
    </label><br/>

    <input type="submit" value="Сохранить"/>
    <a href="/admin">Отмена</a>
</form>
</body>
</html>
