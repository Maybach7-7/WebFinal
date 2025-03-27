<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login</title>
    <link rel="stylesheet" href="styles.css">
</head>
<body>
<div class="login-container">
    <h2>Вход</h2>
    <form action="" method="POST">
        <label for="login">Логин</label>
        <input type="text" id="login" name="login" required><br/>
        <label for="password">Пароль</label>
        <input type="password" id="password" name="password" required><br/>
        <button type="submit">Войти</button>
    </form>
</div>
</body>
</html>