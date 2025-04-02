<%@ page import="java.util.List" %>
<%@ page import="com.maybach7.formhandler.validator.InputError" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login</title>
</head>
<body>
<div class="login-container">
    <h2>Вход</h2>
    <div class="errors">
        <%
            List<InputError> errors = (List<InputError>) request.getAttribute("errors");
            if(errors!=null) {
                for(var error : errors){
        %>
                    <p style="color:red; padding:10px"><%=error.getMessage()%></p>
        <%
                }
            }
        %>
    </div>
    <form action="/session" method="POST">
        <label for="login">Логин</label>
        <input type="text" id="login" name="login" required><br/>
        <label for="password">Пароль</label>
        <input type="password" id="password" name="password" required><br/>
        <button type="submit">Войти</button>
    </form>
</div>
</body>
</html>